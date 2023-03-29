package com.github.veerdone.yblog.cloud.interact_review.factory.review;

import cn.hutool.core.util.StrUtil;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
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

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

@Component
public class ArticleReviewHandler implements ReviewHandler {
    private static final Logger log = LoggerFactory.getLogger(ArticleReviewHandler.class);

    @Resource
    private ReviewService reviewService;

    @Resource
    private MessageService messageService;

    @DubboReference
    private ArticleClient articleClient;

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
        if (Objects.nonNull(articleInfo)) {
            Message msg = new Message();
            msg.setReceiverId(review.getUserId());
            msg.setMsg(StrUtil.format("您的文章: {} 已通过审核!", articleInfo.getTitle()));
            msg.setMsgType(MessageConstant.ARTICLE);
            messageService.create(msg);
        }
    }

    @Override
    public void reviewFailed(Review review) {
        ArticleInfo articleInfo = articleClient.updateStatusAndGet(review.getItemId(), StatusConstant.REVIEW_FAIL);
        if (Objects.nonNull(articleInfo)) {
            Message msg = new Message();
            msg.setReceiverId(review.getUserId());
            msg.setMsg(
                    StrUtil.format("您的文章: {} 未通过审核, 原因是: {}",
                            articleInfo.getTitle(),
                            review.getFailReason())
            );
            msg.setMsgType(MessageConstant.ARTICLE);
            messageService.create(msg);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ReviewFactory.addReview(ReviewConstant.ARTICLE, this);
    }
}
