package com.github.veerdone.yblog.cloud.interact_review.config;

import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.interact_review.listener.ArticleReviewStreamListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Objects;

@Configuration
public class ArticleReviewStreamListenerConfig implements DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(ArticleReviewStreamListenerConfig.class);

    private StreamMessageListenerContainer<String, ObjectRecord<String, ArticleInfo>> listenerContainer;

    private static final int closeSleepTime = 2;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ArticleReviewStreamListener articleReviewStreamListener;

    private void init() {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(CacheKey.ARTICLE_REVIEW_STREAM_KEY))) {
            redisTemplate.opsForStream().createGroup(CacheKey.ARTICLE_REVIEW_STREAM_KEY, CacheKey.ARTICLE_REVIEW_STREAM_GROUP);
        }
    }

    @Bean("articleReviewStreamSubscription")
    public Subscription subscription(RedisConnectionFactory factory) throws UnknownHostException {
        this.init();
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, ObjectRecord<String, ArticleInfo>> options =
                StreamMessageListenerContainer.StreamMessageListenerContainerOptions
                        .builder()
                        .pollTimeout(Duration.ofSeconds(1))
                        .targetType(ArticleInfo.class)
                        .build();
        this.listenerContainer = StreamMessageListenerContainer.create(factory, options);
        Subscription subscription = listenerContainer.receiveAutoAck(
                Consumer.from(CacheKey.ARTICLE_REVIEW_STREAM_GROUP, InetAddress.getLocalHost().getHostName()),
                StreamOffset.create(CacheKey.ARTICLE_REVIEW_STREAM_KEY, ReadOffset.lastConsumed()),
                articleReviewStreamListener
        );
        listenerContainer.start();
        return subscription;
    }


    @Override
    public void destroy() throws Exception {
        if (Objects.nonNull(listenerContainer)) {
            log.info("[{}] close listener, wait {} seconds...", this.getClass(), closeSleepTime);
            listenerContainer.stop();
            Thread.sleep(2000);
        }
    }
}
