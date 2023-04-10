package com.github.veerdone.yblog.cloud.comment.factory;

import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateCommentDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateReplyCommentDto;
import com.github.veerdone.yblog.cloud.base.client.ArticleClient;
import com.github.veerdone.yblog.cloud.base.convert.CommentConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.Comment;
import com.github.veerdone.yblog.cloud.base.model.ReplyComment;
import com.github.veerdone.yblog.cloud.comment.mapper.CommentMapper;
import com.github.veerdone.yblog.cloud.comment.mapper.ReplyCommentMapper;
import com.github.veerdone.yblog.cloud.common.constant.CommentConstant;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ArticleCommentHandler implements CommentHandler {
    private final CommentMapper commentMapper;

    private final ReplyCommentMapper replyCommentMapper;

    private ArticleClient articleClient;

    public ArticleCommentHandler(CommentMapper commentMapper, ReplyCommentMapper replyCommentMapper) {
        this.commentMapper = commentMapper;
        this.replyCommentMapper = replyCommentMapper;
    }

    @DubboReference
    public void setArticleClient(ArticleClient articleClient) {
        this.articleClient = articleClient;
    }

    @Override
    public Comment createComment(CreateCommentDto dto) {
        ArticleInfo articleInfo = articleClient.getById(dto.getItemId());
        if (Objects.isNull(articleInfo)) {
            throw new ServiceException(ServiceExceptionEnum.PARAM_MISTAKE);
        }

        Comment comment = CommentConvert.INSTANCE.toComment(dto);
        commentMapper.insert(comment);

        return comment;
    }

    @Override
    public ReplyComment createReplyComment(CreateReplyCommentDto dto) {
        ReplyComment replyComment = CommentConvert.INSTANCE.toReplyComment(dto);
        replyCommentMapper.insert(replyComment);

        return replyComment;
    }

    @Override
    public void reviewThrough(Long itemId) {
        Comment comment = commentMapper.selectById(itemId);
        IncrOrDecrColumnDto dto = new IncrOrDecrColumnDto(comment.getItemId(), "comments", 1);
        articleClient.updateStatusAndIncrOrDecrColumn(comment.getItemId(), StatusConstant.REVIEW_THROUGH, dto);
    }

    @Override
    public int getType() {
        return CommentConstant.ARTICLE;
    }
}
