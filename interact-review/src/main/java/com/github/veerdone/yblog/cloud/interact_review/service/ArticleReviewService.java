package com.github.veerdone.yblog.cloud.interact_review.service;

import com.github.veerdone.yblog.cloud.base.model.ArticleReview;

public interface ArticleReviewService {
    void create(ArticleReview articleReview);

    void throughOrFail(ArticleReview articleReview, Integer status);
}
