package com.github.veerdone.yblog.cloud.comment.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateReplyCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.convert.CommentConvert;
import com.github.veerdone.yblog.cloud.base.model.ReplyComment;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.comment.factory.CommentHandlerStrategyFactory;
import com.github.veerdone.yblog.cloud.comment.mapper.ReplyCommentMapper;
import com.github.veerdone.yblog.cloud.comment.service.MqProvider;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReplyCommentServiceImpl implements ReplyCommentService {
    private final ReplyCommentMapper replyCommentMapper;

    private final MqProvider mqProvider;

    private UserClient userClient;

    public ReplyCommentServiceImpl(ReplyCommentMapper replyCommentMapper, MqProvider mqProvider) {
        this.replyCommentMapper = replyCommentMapper;
        this.mqProvider = mqProvider;
    }

    @DubboReference
    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public void create(CreateReplyCommentDto dto) {
        ReplyComment replyComment = CommentHandlerStrategyFactory.getHandler(dto.getType()).createReplyComment(dto);
        mqProvider.reviewReplyComment(replyComment);
    }

    @Override
    public void deleteByCommentId(Long commentId, Integer type) {
        LambdaQueryWrapper<ReplyComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReplyComment::getCommentId, commentId)
                .eq(ReplyComment::getType, type);
        replyCommentMapper.delete(wrapper);
    }

    @Override
    public void deleteById(Long id) {
        long userId = StpUtil.getLoginIdAsLong();
        Object role = StpUtil.getSession().get("role");

        ReplyComment replyComment = replyCommentMapper.selectById(id);
        if (replyComment.getReplyUserId().equals(userId) || Objects.equals(2, role)) {
            LambdaUpdateWrapper<ReplyComment> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(ReplyComment::getId, id)
                    .or()
                    .eq(ReplyComment::getReplyCommentId, id)
                    .set(ReplyComment::getDeleted, 1);

            replyCommentMapper.update(null, wrapper);
        }
    }

    @Override
    public void updateStatus(Long id, Integer type, Integer status) {
        LambdaUpdateWrapper<ReplyComment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ReplyComment::getId, id)
                .eq(ReplyComment::getType, type)
                .set(ReplyComment::getStatus, status);

        replyCommentMapper.update(null, wrapper);
        if (StatusConstant.REVIEW_THROUGH.equals(status)) {
            CommentHandlerStrategyFactory.getHandler(type).reviewThrough(id);
        }
    }

    @Override
    public List<ReplyCommentVo> list(Long itemId, Long commentId, Integer type) {
        LambdaQueryWrapper<ReplyComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReplyComment::getItemId, itemId)
                .eq(ReplyComment::getCommentId, commentId)
                .eq(ReplyComment::getType, type)
                .eq(ReplyComment::getStatus, 1);
        List<ReplyComment> replyCommentList = replyCommentMapper.selectList(wrapper);
        if (CollectionUtil.isEmpty(replyCommentList)) {
            return Collections.emptyList();
        }

        List<Long> userIdList = replyCommentList.stream().map(ReplyComment::getReplyUserId).collect(Collectors.toList());
        replyCommentList.stream().map(ReplyComment::getReplyToUserId).forEach(userIdList::add);
        List<UserInfo> userInfoList = userClient.getByIds(userIdList);

        List<ReplyCommentVo> replyCommentVoList = new ArrayList<>();
        for (int i = 0; i < replyCommentList.size(); i++) {
            ReplyComment replyComment = replyCommentList.get(i);
            ReplyCommentVo replyCommentVo = CommentConvert.INSTANCE.toVo(replyComment);
            replyCommentVo.setReplyUserInfo(userInfoList.get(i));
            replyCommentVo.setReplyToUserInfo(userInfoList.get(i+replyCommentList.size()));
            replyCommentVoList.add(replyCommentVo);
        }

        return replyCommentVoList;
    }
}
