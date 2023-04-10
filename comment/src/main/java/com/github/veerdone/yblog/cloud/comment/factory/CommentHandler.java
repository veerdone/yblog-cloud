package com.github.veerdone.yblog.cloud.comment.factory;

import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateCommentDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateReplyCommentDto;
import com.github.veerdone.yblog.cloud.base.model.Comment;
import com.github.veerdone.yblog.cloud.base.model.ReplyComment;
import org.springframework.beans.factory.InitializingBean;

public interface CommentHandler extends InitializingBean {
    Comment createComment(CreateCommentDto dto);

    ReplyComment createReplyComment(CreateReplyCommentDto dto);

    void reviewThrough(Long itemId);

    int getType();

    @Override
    default void afterPropertiesSet() throws Exception {
        CommentHandlerStrategyFactory.registerHandler(this.getType(), this);
    }
}
