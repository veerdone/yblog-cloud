package com.github.veerdone.yblog.cloud.interact_review.service.impl;

import com.github.veerdone.yblog.cloud.base.client.ArticleClient;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.ArticleReview;
import com.github.veerdone.yblog.cloud.interact_review.mapper.ArticleReviewMapper;
import com.github.veerdone.yblog.cloud.interact_review.service.ArticleReviewService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleReviewServiceImpl implements ArticleReviewService {
    @Resource
    private ArticleReviewMapper articleReviewMapper;

    @DubboReference
    private ArticleClient articleClient;

    @Override
    public void create(ArticleReview articleReview) {
        articleReviewMapper.insert(articleReview);
    }

    @Override
    public void throughOrFail(ArticleReview articleReview, Integer status) {
        articleReview.setStatus(status);
        articleReviewMapper.updateById(articleReview);

        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setId(articleReview.getArticleId());
        articleInfo.setStatus(status);
        articleClient.updateById(articleInfo);

        // 调用消息通知
    }
}
