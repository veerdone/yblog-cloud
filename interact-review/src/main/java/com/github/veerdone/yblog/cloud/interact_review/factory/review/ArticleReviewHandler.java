package com.github.veerdone.yblog.cloud.interact_review.factory.review;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleReviewVo;
import com.github.veerdone.yblog.cloud.base.api.article.ArticleClientGrpc;
import com.github.veerdone.yblog.cloud.base.api.article.QueryArticleByIdsReq;
import com.github.veerdone.yblog.cloud.base.api.article.UpdateStatusAndGetArticleReq;
import com.github.veerdone.yblog.cloud.base.api.article.UpdateStatusAndGetArticleResp;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.convert.ReviewConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.Message;
import com.github.veerdone.yblog.cloud.base.model.Review;
import com.github.veerdone.yblog.cloud.common.constant.MessageConstant;
import com.github.veerdone.yblog.cloud.common.constant.ReviewConstant;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import com.github.veerdone.yblog.cloud.common.util.ByteBufferUtil;
import com.github.veerdone.yblog.cloud.interact_review.service.MessageService;
import com.github.veerdone.yblog.cloud.interact_review.service.ReviewService;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ArticleReviewHandler implements ReviewHandler {
    private static final Logger log = LoggerFactory.getLogger(ArticleReviewHandler.class);

    private final ReviewService reviewService;

    private final MessageService messageService;

    private final ArticleClientGrpc.ArticleClientBlockingStub articleClientBlockingStub;

    public ArticleReviewHandler(ReviewService reviewService, MessageService messageService,
                                ArticleClientGrpc.ArticleClientBlockingStub articleClientBlockingStub) {
        this.reviewService = reviewService;
        this.messageService = messageService;
        this.articleClientBlockingStub = articleClientBlockingStub;
    }

    @Override
    public void review(MessageView messageView) throws IOException {
        ByteBuffer body = messageView.getBody();
        ArticleInfo articleInfo = ByteBufferUtil.readValue(body, ArticleInfo.class);
        Optional.ofNullable(articleInfo).ifPresent(info -> {
            Review review = new Review();
            review.setItemId(articleInfo.getId());
            review.setItemType(ReviewConstant.ARTICLE_TYPE);
            review.setUserId(articleInfo.getUserId());

            reviewService.create(review);
        });
    }

    @Override
    public void reviewThrough(Review review) {
        UpdateStatusAndGetArticleReq req = UpdateStatusAndGetArticleReq.newBuilder()
                .setId(review.getItemId())
                .setStatus(StatusConstant.REVIEW_THROUGH)
                .build();
        UpdateStatusAndGetArticleResp resp = articleClientBlockingStub.updateStatusAndGet(req);
        ArticleInfo articleInfo = ArticleConvert.INSTANCE.toArticleInfo(resp.getArticleInfo());
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
        UpdateStatusAndGetArticleReq req = UpdateStatusAndGetArticleReq.newBuilder()
                .setId(review.getItemId())
                .setStatus(StatusConstant.REVIEW_THROUGH)
                .build();
        UpdateStatusAndGetArticleResp resp = articleClientBlockingStub.updateStatusAndGet(req);
        ArticleInfo articleInfo = ArticleConvert.INSTANCE.toArticleInfo(resp.getArticleInfo());

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
    public List<Review> list(Integer type) {
        LambdaQueryWrapper<Review> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Review::getDone, StatusConstant.REVIEW_NOT_DONE)
                .eq(Review::getItemType, type)
                .eq(Review::getStatus, StatusConstant.REVIEW);
        List<Review> reviewList = reviewService.listByWrapper(wrapper);
        if (CollectionUtil.isEmpty(reviewList)) {
            return Collections.emptyList();
        }

        List<Long> itemIdList = reviewList.stream().map(Review::getItemId).collect(Collectors.toList());
        QueryArticleByIdsReq req = QueryArticleByIdsReq.newBuilder()
                .addAllIds(itemIdList)
                .build();
        List<com.github.veerdone.yblog.cloud.base.api.article.ArticleInfo> respArticleInfoList = articleClientBlockingStub.queryArticleInfoByIds(req).getArticleInfosList();
        List<ArticleInfo> articleInfoList = new ArrayList<>(respArticleInfoList.size());
        respArticleInfoList.forEach(articleInfo -> articleInfoList.add(ArticleConvert.INSTANCE.toArticleInfo(articleInfo)));

        List<Review> list = new ArrayList<>(reviewList.size());
        for (int i = 0; i < reviewList.size(); i++) {
            ArticleReviewVo articleReviewVo = ReviewConvert.INSTANCE.toArticleReviewVo(reviewList.get(i));
            articleReviewVo.setArticleInfo(articleInfoList.get(i));
            list.add(articleReviewVo);
        }

        return list;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ReviewFactory.addReview(ReviewConstant.ARTICLE_PROPERTIES, this);
    }
}
