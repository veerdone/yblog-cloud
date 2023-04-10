package com.github.veerdone.yblog.cloud.comment.api;

import com.github.veerdone.yblog.cloud.base.client.CommentClient;
import com.github.veerdone.yblog.cloud.comment.service.CommentService;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import com.github.veerdone.yblog.cloud.common.constant.ReviewConstant;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

@Component
@DubboService
public class CommentClientImpl implements CommentClient {
    private final CommentService commentService;

    private final ReplyCommentService replyCommentService;

    public CommentClientImpl(CommentService commentService, ReplyCommentService replyCommentService) {
        this.commentService = commentService;
        this.replyCommentService = replyCommentService;
    }

    @Override
    public void updateStatus(Long id, Integer commentType, Integer status, Integer extra) {
        if (ReviewConstant.EXTRA_COMMENT.equals(extra)) {
            commentService.updateStatus(id, commentType, status);
        } else if (ReviewConstant.EXTRA_REPLY_COMMENT.equals(extra)) {
            replyCommentService.updateStatus(id, commentType, status);
        }
    }
}
