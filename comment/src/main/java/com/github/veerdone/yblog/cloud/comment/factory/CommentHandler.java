package com.github.veerdone.yblog.cloud.comment.factory;

import org.springframework.beans.factory.InitializingBean;

public interface CommentHandler extends InitializingBean {
    void createComment();

    void createReplyComment();

    int getType();

    @Override
    default void afterPropertiesSet() throws Exception {
        CommentHandlerStrategyFactory.registerHandler(this.getType(), this);
    }
}
