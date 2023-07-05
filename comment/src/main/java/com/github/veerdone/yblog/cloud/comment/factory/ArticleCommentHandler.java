package com.github.veerdone.yblog.cloud.comment.factory;

import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateCommentDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateReplyCommentDto;
import com.github.veerdone.yblog.cloud.base.api.IncrOrDecrColumnReq;
import com.github.veerdone.yblog.cloud.base.api.article.ArticleClientGrpc;
import com.github.veerdone.yblog.cloud.base.api.article.QueryArticleByIdReq;
import com.github.veerdone.yblog.cloud.base.api.article.QueryArticleByIdResp;
import com.github.veerdone.yblog.cloud.base.api.article.UpdateStatusAndIncrOrDecrColumnReq;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
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
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ArticleCommentHandler implements CommentHandler {
    private final CommentMapper commentMapper;

    private final ReplyCommentMapper replyCommentMapper;

    private final ArticleClientGrpc.ArticleClientBlockingStub articleClientBlockingStub;

    public ArticleCommentHandler(CommentMapper commentMapper, ReplyCommentMapper replyCommentMapper,
                                 ArticleClientGrpc.ArticleClientBlockingStub articleClientBlockingStub) {
        this.commentMapper = commentMapper;
        this.replyCommentMapper = replyCommentMapper;
        this.articleClientBlockingStub = articleClientBlockingStub;
    }

    @Override
    public Comment createComment(CreateCommentDto dto) {
        QueryArticleByIdResp resp = articleClientBlockingStub
                .queryArticleInfoById(QueryArticleByIdReq.newBuilder().setId(dto.getItemId()).build());
        ArticleInfo articleInfo = ArticleConvert.INSTANCE.toArticleInfo(resp.getArticleInfo());
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
        IncrOrDecrColumnReq req = IncrOrDecrColumnReq.newBuilder()
                .setColumn("comments")
                .setNum(1)
                .setItemId(comment.getItemId())
                .build();
        UpdateStatusAndIncrOrDecrColumnReq updateStatusAndIncrOrDecrColumnReq = UpdateStatusAndIncrOrDecrColumnReq.newBuilder()
                .setId(comment.getItemId())
                .setStatus(StatusConstant.REVIEW_THROUGH)
                .setIncrOrDecrColumn(req)
                .build();
        articleClientBlockingStub.updateStatusAndIncrOrDecrColumn(updateStatusAndIncrOrDecrColumnReq);
    }

    @Override
    public int getType() {
        return CommentConstant.ARTICLE;
    }
}
