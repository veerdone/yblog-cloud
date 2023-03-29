package com.github.veerdone.yblog.cloud.interact_review.service.impl;

import com.github.veerdone.yblog.cloud.base.model.ThumbsUp;
import com.github.veerdone.yblog.cloud.interact_review.factory.thumbs.ThumbsUpHandlerStrategyFactory;
import com.github.veerdone.yblog.cloud.interact_review.service.ThumbsUpService;
import org.springframework.stereotype.Service;

@Service
public class ThumbsUpServiceImpl implements ThumbsUpService {
    @Override
    public void create(ThumbsUp thumbsUp) {
        ThumbsUpHandlerStrategyFactory.getHandler(thumbsUp.getItemType()).save(thumbsUp);
    }

    @Override
    public void cancel(ThumbsUp thumbsUp) {
        ThumbsUpHandlerStrategyFactory.getHandler(thumbsUp.getItemType()).cancel(thumbsUp);
    }
}
