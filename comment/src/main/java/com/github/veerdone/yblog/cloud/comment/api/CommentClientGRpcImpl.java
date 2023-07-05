package com.github.veerdone.yblog.cloud.comment.api;

import com.github.veerdone.yblog.cloud.base.api.comment.CommentClientGrpc;
import com.github.veerdone.yblog.cloud.base.api.comment.ListCommentContentByIdExtraMapReq;
import com.github.veerdone.yblog.cloud.base.api.comment.ListCommentContentByIdExtraMapResp;
import com.github.veerdone.yblog.cloud.base.api.comment.UpdateCommentStatusReq;
import com.github.veerdone.yblog.cloud.base.api.comment.UpdateCommentStatusResp;
import com.github.veerdone.yblog.cloud.base.model.Comment;
import com.github.veerdone.yblog.cloud.base.model.ReplyComment;
import com.github.veerdone.yblog.cloud.comment.service.CommentService;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import com.github.veerdone.yblog.cloud.common.constant.ReviewConstant;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@GrpcService
public class CommentClientGRpcImpl extends CommentClientGrpc.CommentClientImplBase {
    private final CommentService commentService;

    private final ReplyCommentService replyCommentService;

    public CommentClientGRpcImpl(CommentService commentService, ReplyCommentService replyCommentService) {
        this.commentService = commentService;
        this.replyCommentService = replyCommentService;
    }

    @Override
    public void updateStatus(UpdateCommentStatusReq request, StreamObserver<UpdateCommentStatusResp> responseObserver) {
        if (ReviewConstant.EXTRA_COMMENT.equals(request.getExtra())) {
            commentService.updateStatus(request.getId(), request.getCommentType(), request.getStatus());
        } else if (ReviewConstant.EXTRA_REPLY_COMMENT.equals(request.getExtra())) {
            replyCommentService.updateStatus(request.getId(), request.getCommentType(), request.getStatus());
        }
    }

    @Override
    public void listCommentContentByIdExtraMap(ListCommentContentByIdExtraMapReq request, StreamObserver<ListCommentContentByIdExtraMapResp> responseObserver) {
        Map<Long, Integer> idExtraMap = request.getIdExtraMapMap();
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

        ListCommentContentByIdExtraMapResp resp = ListCommentContentByIdExtraMapResp.newBuilder()
                .addAllCommentContents(list)
                .build();

        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
