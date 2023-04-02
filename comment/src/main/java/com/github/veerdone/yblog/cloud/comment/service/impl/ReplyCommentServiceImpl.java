package com.github.veerdone.yblog.cloud.comment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateReplyCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.convert.CommentConvert;
import com.github.veerdone.yblog.cloud.base.model.ReplyComment;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.comment.factory.CommentHandlerStrategyFactory;
import com.github.veerdone.yblog.cloud.comment.mapper.ReplyCommentMapper;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyCommentServiceImpl implements ReplyCommentService {
    private final ReplyCommentMapper replyCommentMapper;

    @DubboReference
    private UserClient userClient;

    public ReplyCommentServiceImpl(ReplyCommentMapper replyCommentMapper) {
        this.replyCommentMapper = replyCommentMapper;
    }

    @Override
    public void create(CreateReplyCommentDto dto) {
        CommentHandlerStrategyFactory.getHandler(dto.getType()).createReplyComment(dto);
    }

    @Override
    public void deleteByCommentId(Long commentId, Integer type) {
        LambdaQueryWrapper<ReplyComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReplyComment::getCommentId, commentId)
                .eq(ReplyComment::getType, type);
        replyCommentMapper.delete(wrapper);
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

        List<Long> replyUserInfoIds = replyCommentList.stream().map(ReplyComment::getReplyUserId).collect(Collectors.toList());
        List<Long> replyToUserInfoIds = replyCommentList.stream().map(ReplyComment::getReplyToUserId).collect(Collectors.toList());
        List<UserInfo> replyUserInfoList = userClient.getByIds(replyUserInfoIds);
        List<UserInfo> replyToUserInfoList = userClient.getByIds(replyToUserInfoIds);

        List<ReplyCommentVo> replyCommentVoList = new ArrayList<>();
        for (int i = 0; i < replyCommentList.size(); i++) {
            ReplyComment replyComment = replyCommentList.get(i);
            ReplyCommentVo replyCommentVo = CommentConvert.INSTANCE.toVo(replyComment);
            replyCommentVo.setReplyUserInfo(replyUserInfoList.get(i));
            replyCommentVo.setReplyToUserInfo(replyToUserInfoList.get(i));
            replyCommentVoList.add(replyCommentVo);
        }

        return replyCommentVoList;
    }
}
