package com.github.veerdone.yblog.cloud.comment.factory;

import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateCommentDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateReplyCommentDto;
import org.springframework.beans.factory.InitializingBean;

public interface CommentHandler extends InitializingBean {
    void createComment(CreateCommentDto dto);

    void createReplyComment(CreateReplyCommentDto dto);

    int getType();

    @Override
    default void afterPropertiesSet() throws Exception {
        CommentHandlerStrategyFactory.registerHandler(this.getType(), this);
    }
}
