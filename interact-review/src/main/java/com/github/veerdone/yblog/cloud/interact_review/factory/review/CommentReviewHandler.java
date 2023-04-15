package com.github.veerdone.yblog.cloud.interact_review.factory.review;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.comment.ReviewCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.CommentReviewVo;
import com.github.veerdone.yblog.cloud.base.client.CommentClient;
import com.github.veerdone.yblog.cloud.base.convert.ReviewConvert;
import com.github.veerdone.yblog.cloud.base.model.Review;
import com.github.veerdone.yblog.cloud.common.constant.ReviewConstant;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import com.github.veerdone.yblog.cloud.common.util.ByteBufferUtil;
import com.github.veerdone.yblog.cloud.interact_review.service.ReviewService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public List<Review> list(Integer type) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getDone, StatusConstant.REVIEW_NOT_DONE)
                .eq(Review::getItemType, type)
                .eq(Review::getStatus, StatusConstant.REVIEW);
        List<Review> reviewList = reviewService.listByWrapper(wrapper);
        if (CollectionUtil.isEmpty(reviewList)) {
            return Collections.emptyList();
        }

        Map<Long, Integer> idExtraMap = new HashMap<>(reviewList.size());
        reviewList.forEach(review -> idExtraMap.put(review.getItemId(), review.getExtra()));
        List<String> commentContentList = commentClient.listCommentContentByIdExtraMap(idExtraMap);

        List<Review> list = new ArrayList<>(reviewList.size());
        for (int i = 0; i < reviewList.size(); i++) {
            CommentReviewVo commentReviewVo = ReviewConvert.INSTANCE.toCommentReviewVo(reviewList.get(i));
            if (i < commentContentList.size()) {
                commentReviewVo.setCommentContent(commentContentList.get(i));
            } else {
                commentReviewVo.setCommentContent("");
            }
        }

        return list;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ReviewFactory.addReview(ReviewConstant.COMMENT_PROPERTIES, this);
    }
}
