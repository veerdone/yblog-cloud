package com.github.veerdone.yblog.cloud.interact_review.service;

import com.github.veerdone.yblog.cloud.base.model.ThumbsUp;

public interface ThumbsUpService {

    void create(ThumbsUp thumbsUp);

    void cancel(ThumbsUp thumbsUp);
}
