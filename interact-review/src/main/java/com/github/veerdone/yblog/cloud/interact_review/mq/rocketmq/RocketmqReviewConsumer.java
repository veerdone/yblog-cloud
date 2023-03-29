package com.github.veerdone.yblog.cloud.interact_review.mq.rocketmq;

import com.github.veerdone.yblog.cloud.common.constant.ReviewConstant;
import com.github.veerdone.yblog.cloud.common.constant.RocketMqConst;
import com.github.veerdone.yblog.cloud.common.util.async.AsyncExecutor;
import com.github.veerdone.yblog.cloud.interact_review.factory.review.ReviewFactory;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.FilterExpressionType;
import org.apache.rocketmq.client.apis.consumer.SimpleConsumer;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Component
public class RocketmqReviewConsumer implements InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(RocketmqReviewConsumer.class);

    @Value("${rocketmq.endpoints}")
    private String endpoints;

    @Value("${rocketmq.topic}")
    private String topic;

    @Value("${rocketmq.awaitSecond}")
    private Long awaitSecond;

    private SimpleConsumer simpleConsumer;

    @Override
    public void afterPropertiesSet() throws Exception {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfiguration configuration = ClientConfiguration.newBuilder().setEndpoints(endpoints).build();
        FilterExpression filterExpression = new FilterExpression(RocketMqConst.REVIEW_TAG, FilterExpressionType.TAG);
        simpleConsumer = provider.newSimpleConsumerBuilder()
                .setConsumerGroup(RocketMqConst.REVIEW_CONSUME_GROUP)
                .setClientConfiguration(configuration)
                .setSubscriptionExpressions(Collections.singletonMap(topic, filterExpression))
                .setAwaitDuration(Duration.ofSeconds(awaitSecond))
                .build();

        AsyncExecutor.execute(() -> {
            while (true) {
                try {
                    List<MessageView> messageViewList = simpleConsumer.receive(10, Duration.ofSeconds(30));
                    messageViewList.forEach(messageView -> {
                        try {
                            String type = messageView.getProperties().get(ReviewConstant.MQ_PROPERTIES_KEY);
                            ReviewFactory.getReview(type).review(messageView);

                            simpleConsumer.ack(messageView);
                        } catch (ClientException e) {
                            log.warn("failed to ack message, messageId={}, reason: ", messageView.getMessageId(), e);
                        } catch (IOException e) {
                            log.warn("failed to consume message, messageId={}, reason: ", messageView.getMessageId(), e);
                        }
                    });
                } catch (ClientException e) {
                    log.warn("rocketmq receive msg fail: ", e);
                }
            }
        });
    }
}
