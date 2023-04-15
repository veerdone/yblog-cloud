package com.github.veerdone.yblog.cloud.interact_review.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.github.veerdone.yblog.cloud.base.Dto.review.UpdateReviewDto;
import com.github.veerdone.yblog.cloud.base.model.Review;

import java.util.List;

public interface ReviewService {
    void create(Review review);

    void reviewThrough(UpdateReviewDto dto);

    void reviewFailed(UpdateReviewDto dto);

    List<Review> listByWrapper(Wrapper<Review> wrapper);

    List<Review> list(Integer type);
}
