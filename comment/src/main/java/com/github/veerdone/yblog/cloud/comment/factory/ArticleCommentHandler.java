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
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

@Component
public class ArticleCommentHandler implements CommentHandler {
    private final CommentMapper commentMapper;

    private final ReplyCommentMapper replyCommentMapper;

    @DubboReference
    private ArticleClient articleClient;

    public ArticleCommentHandler(CommentMapper commentMapper, ReplyCommentMapper replyCommentMapper) {
        this.commentMapper = commentMapper;
        this.replyCommentMapper = replyCommentMapper;
    }

    @Override
    public void createComment(CreateCommentDto dto) {
        ArticleInfo article = articleClient.getById(dto.getItemId());
        if (Objects.isNull(article)) {
            throw new ServiceException(ServiceExceptionEnum.PARAM_MISTAKE);
        }

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
