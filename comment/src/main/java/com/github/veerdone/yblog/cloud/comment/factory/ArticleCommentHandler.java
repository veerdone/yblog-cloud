package com.github.veerdone.yblog.cloud.comment.factory;

import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateCommentDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateReplyCommentDto;
import com.github.veerdone.yblog.cloud.base.client.ArticleClient;
import com.github.veerdone.yblog.cloud.base.convert.CommentConvert;
import com.github.veerdone.yblog.cloud.base.model.Comment;
import com.github.veerdone.yblog.cloud.base.model.ReplyComment;
import com.github.veerdone.yblog.cloud.comment.mapper.CommentMapper;
import com.github.veerdone.yblog.cloud.comment.mapper.ReplyCommentMapper;
import com.github.veerdone.yblog.cloud.common.constant.CommentConstant;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ArticleCommentHandler implements CommentHandler {
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private ReplyCommentMapper replyCommentMapper;

    @DubboReference
    private ArticleClient articleClient;

    @Override
    public void createComment(CreateCommentDto dto) {
        Comment comment = CommentConvert.INSTANCE.toComment(dto);
        commentMapper.insert(comment);

        articleClient.incrOrDecrColumn(new IncrOrDecrColumnDto(dto.getItemId(), "comments", 1));
    }

    @Override
    public void createReplyComment(CreateReplyCommentDto dto) {
        ReplyComment replyComment = CommentConvert.INSTANCE.toReplyComment(dto);
        replyCommentMapper.insert(replyComment);

        articleClient.incrOrDecrColumn(new IncrOrDecrColumnDto(dto.getItemId(), "comments", 1));
    }

    @Override
    public int getType() {
        return CommentConstant.ARTICLE;
    }
}
