package com.github.veerdone.yblog.cloud.article.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.veerdone.yblog.cloud.article.service.MqProvider;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
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

import javax.annotation.Resource;

@Service
public class RocketMqProviderImpl implements MqProvider, InitializingBean, DisposableBean {
    private static final Logger log = LoggerFactory.getLogger(RocketMqProviderImpl.class);

    @Value("${rocketmq.endpoints}")
    private String endpoints;

    @Value("${rocketmq.topic}")
    private String topic;

    @Resource
    private ObjectMapper objectMapper;

    private Producer producer;

    @Override
    public void reviewArticle(ArticleInfo articleInfo) {
        try {
            byte[] body = objectMapper.writeValueAsBytes(articleInfo);
            Message message = new MessageBuilderImpl()
                    .setTopic(topic)
                    .setTag(RocketMqConst.REVIEW_TAG)
                    .addProperty(ReviewConstant.MQ_PROPERTIES_KEY, ReviewConstant.ARTICLE)
                    .setBody(body)
                    .build();
            producer.send(message);
        } catch (JsonProcessingException e) {
            log.warn("parse articleInfo to json bytes fail, article id: {}, reason: {},", articleInfo.getId(), e);
        } catch (ClientException e) {
            log.warn("rocketmq send article review message fail, article id: {}, reason: {}", articleInfo.getId(), e);
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

    public void destroy() throws Exception {
        producer.close();
    }
}
