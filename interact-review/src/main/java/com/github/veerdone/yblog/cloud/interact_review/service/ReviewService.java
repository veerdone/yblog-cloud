package com.github.veerdone.yblog.cloud.interact_review.service;

import com.github.veerdone.yblog.cloud.base.model.Review;

public interface ReviewService {
    void create(Review review);

    void reviewThrough(Review review);

    void reviewFailed(Review review);
}
