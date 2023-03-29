package com.github.veerdone.yblog.cloud.interact_review.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.model.Review;
import com.github.veerdone.yblog.cloud.interact_review.factory.review.ReviewFactory;
import com.github.veerdone.yblog.cloud.interact_review.mapper.ReviewMapper;
import com.github.veerdone.yblog.cloud.interact_review.service.ReviewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Resource
    private ReviewMapper reviewMapper;

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
    public void reviewThrough(Review review) {
        int i = reviewMapper.updateById(review);
        if (i > 0) {
            ReviewFactory.getReview(String.valueOf(review.getItemType())).reviewThrough(review);
        }
    }

    @Override
    public void reviewFailed(Review review) {
        int i = reviewMapper.updateById(review);
        if (i > 0) {
            ReviewFactory.getReview(String.valueOf(review.getItemType())).reviewFailed(review);
        }
    }
}
