package com.github.veerdone.yblog.cloud.base.client;

public interface CommentClient {
    void updateStatus(Long id, Integer commentType, Integer status, Integer extra);
}
