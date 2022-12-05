package com.github.veerdone.yblog.cloud.interact_review.listener;

import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.ArticleReview;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.interact_review.service.ArticleReviewService;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ArticleReviewStreamListener implements StreamListener<String, ObjectRecord<String, ArticleInfo>> {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ArticleReviewService articleReviewService;


    @Override
    public void onMessage(ObjectRecord<String, ArticleInfo> message) {
        ArticleInfo articleInfo = message.getValue();
        ArticleReview review = new ArticleReview();
        review.setArticleId(articleInfo.getId());
        review.setArticleTitle(articleInfo.getTitle());
        review.setStatus(articleInfo.getStatus());
        review.setUserId(articleInfo.getUserId());
        articleReviewService.create(review);

        redisTemplate.opsForStream().delete(CacheKey.ARTICLE_REVIEW_STREAM_KEY, message.getId());
    }
}
