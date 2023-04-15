package com.github.veerdone.yblog.cloud.comment.api;

import com.github.veerdone.yblog.cloud.base.client.CommentClient;
import com.github.veerdone.yblog.cloud.base.model.Comment;
import com.github.veerdone.yblog.cloud.base.model.ReplyComment;
import com.github.veerdone.yblog.cloud.comment.service.CommentService;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import com.github.veerdone.yblog.cloud.common.constant.ReviewConstant;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @Override
    public List<String> listCommentContentByIdExtraMap(Map<Long, Integer> idExtraMap) {
        List<String> list = new ArrayList<>(idExtraMap.size());
        idExtraMap.forEach((id, extra) -> {
            if (ReviewConstant.EXTRA_COMMENT.equals(extra)) {
                Comment comment = commentService.getById(id);
                Optional.ofNullable(comment).ifPresent(c -> list.add(c.getCommentContent()));
            } else {
                ReplyComment replyComment = replyCommentService.getById(id);
                Optional.ofNullable(replyComment).ifPresent(rc -> list.add(rc.getReplyCommentContent()));
            }
        });

        return list;
    }
}
