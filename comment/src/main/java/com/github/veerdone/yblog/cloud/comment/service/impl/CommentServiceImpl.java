package com.github.veerdone.yblog.cloud.comment.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateCommentDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.QueryListCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.CommentVo;
import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.convert.CommentConvert;
import com.github.veerdone.yblog.cloud.base.model.Comment;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.comment.factory.CommentHandlerStrategyFactory;
import com.github.veerdone.yblog.cloud.comment.mapper.CommentMapper;
import com.github.veerdone.yblog.cloud.comment.service.CommentService;
import com.github.veerdone.yblog.cloud.comment.service.MqProvider;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import com.github.veerdone.yblog.cloud.common.page.Page;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;

    private final ReplyCommentService replyCommentService;

    private final MqProvider mqProvider;

    private UserClient userClient;

    public CommentServiceImpl(CommentMapper commentMapper, ReplyCommentService replyCommentService, MqProvider mqProvider) {
        this.commentMapper = commentMapper;
        this.replyCommentService = replyCommentService;
        this.mqProvider = mqProvider;
    }

    @DubboReference
    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public void create(CreateCommentDto dto) {
        Comment comment = CommentHandlerStrategyFactory.getHandler(dto.getType()).createComment(dto);
        mqProvider.reviewComment(comment);
    }

    @Override
    public void delete(Long id, Integer type) {
        long userId = StpUtil.getLoginIdAsLong();
        Object role = StpUtil.getSession().get("role");

        Comment comment = commentMapper.selectById(id);
        if (comment.getUserId().equals(userId) || Objects.equals(2, role)) {
            replyCommentService.deleteByCommentId(id, type);
            commentMapper.deleteById(id);
        }
    }

    @Override
    public Comment getById(Long id) {
        return this.commentMapper.selectById(id);
    }

    @Override
    public void updateStatus(Long id, Integer type, Integer status) {
        LambdaUpdateWrapper<Comment> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Comment::getId, id)
                .eq(Comment::getType, type)
                .set(Comment::getStatus, status);

        commentMapper.update(null, wrapper);
        if (StatusConstant.REVIEW_THROUGH.equals(status)) {
            CommentHandlerStrategyFactory.getHandler(type).reviewThrough(id);
        }
    }

    @Override
    @Page
    public List<CommentVo> listByQuery(QueryListCommentDto dto) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getItemId, dto.getItemId())
                .eq(Comment::getType, dto.getType())
                .eq(Comment::getStatus, 1);
        List<Comment> commentList = commentMapper.selectList(wrapper);
        if (CollectionUtil.isEmpty(commentList)) {
            return Collections.emptyList();
        }

        List<Long> userIds = commentList.stream().map(Comment::getUserId).collect(Collectors.toList());
        System.out.println(userIds.getClass());
        List<UserInfo> userInfoList = userClient.getByIds(userIds);

        List<CommentVo> commentVoList = new ArrayList<>();
        for (int i = 0; i < commentList.size(); i++) {
            Comment comment = commentList.get(i);
            CommentVo commentVo = CommentConvert.INSTANCE.toVo(comment);
            commentVo.setUserInfo(userInfoList.get(i));
            List<ReplyCommentVo> replyCommentVoList = replyCommentService.list(comment.getItemId(), comment.getId(), dto.getType());
            commentVo.setReplyCommentList(replyCommentVoList);
            commentVoList.add(commentVo);
        }

        return commentVoList;
    }
}
