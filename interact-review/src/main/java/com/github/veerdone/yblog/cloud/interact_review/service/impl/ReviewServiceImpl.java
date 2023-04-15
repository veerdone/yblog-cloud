package com.github.veerdone.yblog.cloud.interact_review.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.review.UpdateReviewDto;
import com.github.veerdone.yblog.cloud.base.model.Review;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import com.github.veerdone.yblog.cloud.common.page.Page;
import com.github.veerdone.yblog.cloud.interact_review.factory.review.ReviewFactory;
import com.github.veerdone.yblog.cloud.interact_review.factory.review.ReviewHandler;
import com.github.veerdone.yblog.cloud.interact_review.mapper.ReviewMapper;
import com.github.veerdone.yblog.cloud.interact_review.service.ReviewService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper reviewMapper;

    public ReviewServiceImpl(ReviewMapper reviewMapper) {
        this.reviewMapper = reviewMapper;
    }

    @Override
    public void create(Review review) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getItemId, review.getItemId())
                .eq(Review::getDone, 0)
                .eq(Review::getItemType, review.getItemType());
        if (Objects.nonNull(reviewMapper.selectOne(wrapper))) {
            return;
        }

        reviewMapper.insert(review);
    }

    @Override
    public void reviewThrough(UpdateReviewDto dto) {
        Review dbReview = reviewMapper.selectById(dto.getId());
        Optional.ofNullable(dbReview).ifPresent(review -> {
            review.setReviewUserId(dto.getReviewUserId());
            review.setStatus(StatusConstant.REVIEW_THROUGH);
            review.setDone(StatusConstant.REVIEW_DONE);

            int i = reviewMapper.updateById(review);
            if (i > 0) {
                ReviewFactory.getReview(String.valueOf(review.getItemType())).reviewThrough(review);
            }
        });
    }

    @Override
    public void reviewFailed(UpdateReviewDto dto) {
        Review dbReview = reviewMapper.selectById(dto.getId());
        Optional.ofNullable(dbReview).ifPresent(review -> {
            review.setReviewUserId(dto.getReviewUserId());
            review.setFailReason(dto.getFailReason());
            review.setStatus(StatusConstant.REVIEW_FAIL);
            review.setDone(StatusConstant.REVIEW_DONE);

            int i = reviewMapper.updateById(review);
            if (i > 0) {
                ReviewFactory.getReview(String.valueOf(review.getItemType())).reviewFailed(review);
            }
        });
    }

    @Override
    @Page
    public List<Review> listByWrapper(Wrapper<Review> wrapper) {
        return reviewMapper.selectList(wrapper);
    }

    @Override
    public List<Review> list(Integer type) {
        ReviewHandler review = ReviewFactory.getReview(type.toString());
        if (Objects.isNull(review)) {
            return Collections.emptyList();
        }

        return review.list(type);
    }
}
