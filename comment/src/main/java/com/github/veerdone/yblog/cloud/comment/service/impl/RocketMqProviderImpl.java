package com.github.veerdone.yblog.cloud.comment.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.veerdone.yblog.cloud.base.Dto.comment.ReviewCommentDto;
import com.github.veerdone.yblog.cloud.base.model.Comment;
import com.github.veerdone.yblog.cloud.base.model.ReplyComment;
import com.github.veerdone.yblog.cloud.comment.service.MqProvider;
import com.github.veerdone.yblog.cloud.common.constant.ReviewConstant;
import com.github.veerdone.yblog.cloud.common.constant.RocketMqConst;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.java.message.MessageBuilderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class RocketMqProviderImpl implements MqProvider, InitializingBean, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(RocketMqProviderImpl.class);

    private final String endpoints;

    private final String topic;

    private final ObjectMapper objectMapper;

    public RocketMqProviderImpl(@Value("${rocketmq.endpoints}") String endpoints, @Value("${rocketmq.topic}") String topic,
                                ObjectMapper objectMapper) {
        this.endpoints = endpoints;
        this.topic = topic;
        this.objectMapper = objectMapper;
    }


    private Producer producer;

    @Override
    public void reviewComment(Comment comment) {
        ReviewCommentDto dto = new ReviewCommentDto();
        dto.setItemId(comment.getId());
        dto.setItemType(comment.getType());
        dto.setUserId(comment.getUserId());
        dto.setExtra(ReviewConstant.EXTRA_COMMENT);

        this.sendMessage(dto);
    }

    @Override
    public void reviewReplyComment(ReplyComment replyComment) {
        ReviewCommentDto dto = new ReviewCommentDto();
        dto.setItemId(replyComment.getId());
        dto.setItemType(replyComment.getType());
        dto.setUserId(replyComment.getReplyUserId());
        dto.setExtra(ReviewConstant.EXTRA_REPLY_COMMENT);

        this.sendMessage(dto);
    }

    private void sendMessage(ReviewCommentDto dto) {
        try {
            byte[] body = objectMapper.writeValueAsBytes(dto);
            Message message = new MessageBuilderImpl()
                    .setTopic(topic)
                    .setTag(RocketMqConst.REVIEW_TAG)
                    .addProperty(ReviewConstant.MQ_PROPERTIES_KEY, ReviewConstant.COMMENT_PROPERTIES)
                    .setBody(body)
                    .build();
            producer.send(message);
        } catch (JsonProcessingException e) {
            log.warn("parse articleInfo to json bytes fail, article id: {}, reason: ,", dto.getItemId(), e);
        } catch (ClientException e) {
            log.warn("rocketmq send article review message fail, article id: {}, reason: ", dto.getItemId(), e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfiguration configuration = ClientConfiguration.newBuilder()
                .setEndpoints(endpoints)
                .build();

        producer = provider.newProducerBuilder()
                .setTopics(topic)
                .setClientConfiguration(configuration)
                .build();
    }

    @Override
    public void destroy() throws Exception {
        if (Objects.nonNull(producer)) {
            producer.close();
        }
    }

}
