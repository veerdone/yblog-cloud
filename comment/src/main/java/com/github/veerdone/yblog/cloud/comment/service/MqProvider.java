package com.github.veerdone.yblog.cloud.comment.service;

import com.github.veerdone.yblog.cloud.base.model.Comment;
import com.github.veerdone.yblog.cloud.base.model.ReplyComment;

public interface MqProvider {
    void reviewComment(Comment comment);

    void reviewReplyComment(ReplyComment replyComment);
}
