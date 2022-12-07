package com.github.veerdone.yblog.cloud.interact_review.service.impl;

import com.github.veerdone.yblog.cloud.base.client.ArticleClient;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.ArticleReview;
import com.github.veerdone.yblog.cloud.base.model.Message;
import com.github.veerdone.yblog.cloud.interact_review.mapper.ArticleReviewMapper;
import com.github.veerdone.yblog.cloud.interact_review.service.ArticleReviewService;
import com.github.veerdone.yblog.cloud.interact_review.service.MessageService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ArticleReviewServiceImpl implements ArticleReviewService {
    @Resource
    private ArticleReviewMapper articleReviewMapper;

    @Resource
    private MessageService messageService;

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
        String msg;
        if (status == 1) {
            msg = String.format("您的文章: %s 已通过审核", articleReview.getArticleTitle());
        } else {
            msg = String.format("您的文章: %s 未通过审核, 原因是: %s",
                    articleReview.getArticleTitle(), articleReview.getFailReason());
        }
        Message message = this.buildMessage(msg, articleReview.getUserId());
        messageService.create(message);
    }

    private Message buildMessage(String msg, Long userId) {
        Message message = new Message();
        message.setMsg(msg);
        message.setReceiverId(userId);
        message.setMsgType(1);
        return message;
    }
}
