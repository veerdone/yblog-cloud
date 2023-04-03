package com.github.veerdone.yblog.cloud.interact_review.factory.review;

import cn.hutool.core.util.StrUtil;
import com.github.veerdone.yblog.cloud.base.client.ArticleClient;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.Message;
import com.github.veerdone.yblog.cloud.base.model.Review;
import com.github.veerdone.yblog.cloud.common.constant.MessageConstant;
import com.github.veerdone.yblog.cloud.common.constant.ReviewConstant;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import com.github.veerdone.yblog.cloud.common.util.ByteBufferUtil;
import com.github.veerdone.yblog.cloud.interact_review.service.MessageService;
import com.github.veerdone.yblog.cloud.interact_review.service.ReviewService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;

@Component
public class ArticleReviewHandler implements ReviewHandler {
    private static final Logger log = LoggerFactory.getLogger(ArticleReviewHandler.class);

    private final ReviewService reviewService;

    private final MessageService messageService;

    private ArticleClient articleClient;

    public ArticleReviewHandler(ReviewService reviewService, MessageService messageService) {
        this.reviewService = reviewService;
        this.messageService = messageService;
    }

    @DubboReference
    public void setArticleClient(ArticleClient articleClient) {
        this.articleClient = articleClient;
    }

    @Override
    public void review(MessageView messageView) throws IOException {
        ByteBuffer body = messageView.getBody();
        ArticleInfo articleInfo = ByteBufferUtil.readValue(body, ArticleInfo.class);
        if (Objects.isNull(articleInfo)) {
            return;
        }

        Review review = new Review();
        review.setItemId(articleInfo.getId());
        review.setItemType(ReviewConstant.ARTICLE_TYPE);
        review.setUserId(articleInfo.getUserId());

        reviewService.create(review);
    }

    @Override
    public void reviewThrough(Review review) {
        ArticleInfo articleInfo = articleClient.updateStatusAndGet(review.getItemId(), StatusConstant.REVIEW_THROUGH);
        Optional.ofNullable(articleInfo).ifPresent(info -> {
            Message msg = new Message();
            msg.setReceiverId(review.getUserId());
            msg.setMsg(StrUtil.format("您的文章: {} 已通过审核!", info.getTitle()));
            msg.setMsgType(MessageConstant.ARTICLE);
            messageService.create(msg);
        });
    }

    @Override
    public void reviewFailed(Review review) {
        ArticleInfo articleInfo = articleClient.updateStatusAndGet(review.getItemId(), StatusConstant.REVIEW_FAIL);
        Optional.ofNullable(articleInfo).ifPresent(info -> {
            Message msg = new Message();
            msg.setReceiverId(review.getUserId());
            msg.setMsg(
                    StrUtil.format("您的文章: {} 未通过审核, 原因是: {}",
                            info.getTitle(),
                            review.getFailReason())
            );
            msg.setMsgType(MessageConstant.ARTICLE);
            messageService.create(msg);
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ReviewFactory.addReview(ReviewConstant.ARTICLE, this);
    }
}
