package com.github.veerdone.yblog.cloud.interact_review.factory.review;

import com.github.veerdone.yblog.cloud.base.Dto.comment.ReviewCommentDto;
import com.github.veerdone.yblog.cloud.base.client.CommentClient;
import com.github.veerdone.yblog.cloud.base.model.Review;
import com.github.veerdone.yblog.cloud.common.constant.ReviewConstant;
import com.github.veerdone.yblog.cloud.common.util.ByteBufferUtil;
import com.github.veerdone.yblog.cloud.interact_review.service.ReviewService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Optional;

@Service
public class CommentReviewHandler implements ReviewHandler {
    private final ReviewService reviewService;

    private CommentClient commentClient;

    public CommentReviewHandler(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @DubboReference
    public void setCommentClient(CommentClient commentClient) {
        this.commentClient = commentClient;
    }

    @Override
    public void review(MessageView messageView) throws IOException {
        ByteBuffer body = messageView.getBody();
        ReviewCommentDto reviewCommentDto = ByteBufferUtil.readValue(body, ReviewCommentDto.class);
        Optional.ofNullable(reviewCommentDto).ifPresent(dto -> {
            Review review = new Review();
            review.setItemId(dto.getItemId());
            review.setItemType(dto.getItemType());
            review.setUserId(dto.getUserId());
            review.setExtra(dto.getExtra());

            reviewService.create(review);
        });
    }

    @Override
    public void reviewThrough(Review review) {
        commentClient.updateStatus(review.getItemId(), review.getItemType(), review.getStatus(), review.getExtra());
    }

    @Override
    public void reviewFailed(Review review) {
        commentClient.updateStatus(review.getItemId(), review.getItemType(), review.getStatus(), review.getExtra());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ReviewFactory.addReview(ReviewConstant.COMMENT_PROPERTIES, this);
    }
}
