package com.github.veerdone.yblog.cloud.interact_review.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.veerdone.yblog.cloud.base.model.ThumbsUp;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.common.constant.ThumbsUpConstant;
import com.github.veerdone.yblog.cloud.interact_review.mapper.ThumbsUpMapper;
import com.github.veerdone.yblog.cloud.interact_review.service.ThumbsUpService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ThumbsUpServiceImpl implements ThumbsUpService {
    @Resource
    private ThumbsUpMapper thumbsUpMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void create(ThumbsUp thumbsUp) {
        if (thumbsUp.getItemType().equals(ThumbsUpConstant.ARTICLE)) {
            redisTemplate.opsForSet().add(CacheKey.ARTICLE_THUMBS_UP + thumbsUp.getUserId(), thumbsUp.getItemId());
        }
        int i = this.tryUpdate(thumbsUp, ThumbsUpConstant.LIKE);
        if (i == 0) {
            thumbsUpMapper.insert(thumbsUp);
        }
    }

    @Override
    public void cancel(ThumbsUp thumbsUp) {
        if (thumbsUp.getItemType().equals(ThumbsUpConstant.ARTICLE)) {
            redisTemplate.opsForSet().remove(CacheKey.ARTICLE_THUMBS_UP + thumbsUp.getUserId(), thumbsUp.getItemId());
        }
        int i = this.tryUpdate(thumbsUp, ThumbsUpConstant.UNLIKE);
        if (i == 0) {
            thumbsUp.setStatus(ThumbsUpConstant.UNLIKE);
            thumbsUpMapper.insert(thumbsUp);
        }
    }

    private int tryUpdate(ThumbsUp thumbsUp, Integer status) {
        LambdaUpdateWrapper<ThumbsUp> wrapper = new LambdaUpdateWrapper<>(thumbsUp);
        wrapper.set(ThumbsUp::getStatus, status);
        return thumbsUpMapper.update(null, wrapper);
    }
}
