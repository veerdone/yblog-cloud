package com.github.veerdone.yblog.cloud.comment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateCommentDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.ListQueryCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.CommentVo;
import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.convert.CommentConvert;
import com.github.veerdone.yblog.cloud.base.model.Comment;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.comment.factory.CommentHandlerStrategyFactory;
import com.github.veerdone.yblog.cloud.comment.mapper.CommentMapper;
import com.github.veerdone.yblog.cloud.comment.service.CommentService;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;

    private final ReplyCommentService replyCommentService;

    @DubboReference
    private UserClient userClient;

    public CommentServiceImpl(CommentMapper commentMapper, ReplyCommentService replyCommentService) {
        this.commentMapper = commentMapper;
        this.replyCommentService = replyCommentService;
    }

    @Override
    public void create(CreateCommentDto dto) {
        CommentHandlerStrategyFactory.getHandler(dto.getType()).createComment(dto);
    }

    @Override
    public void delete(Long itemId, Integer type) {
        replyCommentService.deleteByCommentId(itemId, type);

        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getItemId, itemId)
                .eq(Comment::getType, type);
        commentMapper.delete(wrapper);
    }

    @Override
    public List<CommentVo> listByQuery(ListQueryCommentDto dto) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getItemId, dto.getItemId())
                .eq(Comment::getType, dto.getType())
                .eq(Comment::getStatus, 1);
        List<Comment> commentList = commentMapper.selectList(wrapper);
        if (CollectionUtil.isEmpty(commentList)) {
            return Collections.emptyList();
        }

        List<Long> userIds = commentList.stream().map(Comment::getUserId).collect(Collectors.toList());
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
