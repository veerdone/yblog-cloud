package com.github.veerdone.yblog.cloud.interact_review.controller;

import com.github.veerdone.yblog.cloud.base.Dto.review.UpdateReviewDto;
import com.github.veerdone.yblog.cloud.base.convert.ReviewConvert;
import com.github.veerdone.yblog.cloud.base.model.Review;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import com.github.veerdone.yblog.cloud.interact_review.service.ReviewService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PutMapping("/through")
    public void reviewThrough(@RequestBody @Validated UpdateReviewDto dto) {
        Review review = ReviewConvert.INSTANCE.toReview(dto);
        review.setStatus(StatusConstant.REVIEW_THROUGH);
        review.setDone(StatusConstant.REVIEW_DONE);

        reviewService.reviewThrough(review);
    }

    @PutMapping("/fail")
    public void reviewFailed(@RequestBody @Validated UpdateReviewDto dto) {
        Review review = ReviewConvert.INSTANCE.toReview(dto);
        review.setStatus(StatusConstant.REVIEW_FAIL);
        review.setDone(StatusConstant.REVIEW_DONE);

        reviewService.reviewFailed(review);
    }
}
