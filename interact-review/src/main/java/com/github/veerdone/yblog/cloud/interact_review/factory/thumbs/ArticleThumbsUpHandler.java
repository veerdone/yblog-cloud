package com.github.veerdone.yblog.cloud.interact_review.factory.thumbs;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.api.IncrOrDecrColumnReq;
import com.github.veerdone.yblog.cloud.base.api.article.ArticleClientGrpc;
import com.github.veerdone.yblog.cloud.base.api.user.UserClientGrpc;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.convert.UserConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.Message;
import com.github.veerdone.yblog.cloud.base.model.ThumbsUp;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.common.constant.MessageConstant;
import com.github.veerdone.yblog.cloud.common.constant.ThumbsUpConstant;
import com.github.veerdone.yblog.cloud.interact_review.mapper.ThumbsUpMapper;
import com.github.veerdone.yblog.cloud.interact_review.service.MessageService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArticleThumbsUpHandler implements ThumbsUpHandler {
    private final ThumbsUpMapper thumbsUpMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    private final MessageService messageService;

    private final ArticleClientGrpc.ArticleClientBlockingStub articleClientBlockingStub;

    private final UserClientGrpc.UserClientBlockingStub userClientBlockingStub;

    public ArticleThumbsUpHandler(ThumbsUpMapper thumbsUpMapper, RedisTemplate<String, Object> redisTemplate,
                                  MessageService messageService, ArticleClientGrpc.ArticleClientBlockingStub articleClientBlockingStub,
                                  UserClientGrpc.UserClientBlockingStub userClientBlockingStub) {
        this.thumbsUpMapper = thumbsUpMapper;
        this.redisTemplate = redisTemplate;
        this.messageService = messageService;
        this.articleClientBlockingStub = articleClientBlockingStub;
        this.userClientBlockingStub = userClientBlockingStub;
    }

    @Override
    public void save(ThumbsUp thumbsUp) {
        Long i = redisTemplate.opsForSet().add(CacheKey.USER_ARTICLE_THUMBS_UP + thumbsUp.getUserId(), thumbsUp.getItemId());
        if (i != null && i > 0L) {
            thumbsUp.setStatus(ThumbsUpConstant.LIKE);
            thumbsUpMapper.insert(thumbsUp);
            // 文章点赞加1
            IncrOrDecrColumnReq articleReq = IncrOrDecrColumnReq.newBuilder()
                    .setColumn("likes")
                    .setNum(1)
                    .setItemId(thumbsUp.getItemId())
                    .build();
            com.github.veerdone.yblog.cloud.base.api.article.ArticleInfo info = articleClientBlockingStub.incrOrDecrColumnAndGet(articleReq).getArticleInfo();
            ArticleInfo articleInfo = ArticleConvert.INSTANCE.toArticleInfo(info);
            // 用户点赞加1
            IncrOrDecrColumnReq req = IncrOrDecrColumnReq.newBuilder()
                    .setColumn("likes")
                    .setItemId(articleInfo.getUserId())
                    .setNum(1)
                    .build();
            UserInfo userInfo = UserConvert.INSTANCE.toUserInfo(userClientBlockingStub.incrOrDecrColumnAndGet(req).getUserInfo());

            String msg = StrUtil.format("{} 点赞了您的文章: {}", userInfo.getUserName(), articleInfo.getTitle());
            Message message = new Message();
            message.setMsg(msg);
            message.setReceiverId(articleInfo.getUserId());
            message.setMsgType(MessageConstant.THUMBS_UP);
            messageService.create(message);
        }
    }

    @Override
    public void cancel(ThumbsUp thumbsUp) {
        Long i = redisTemplate.opsForSet().remove(CacheKey.USER_ARTICLE_THUMBS_UP + thumbsUp.getUserId(), thumbsUp.getItemId());
        if (i != null && i > 0L) {
            LambdaUpdateWrapper<ThumbsUp> wrapper = new LambdaUpdateWrapper<>(thumbsUp);
            wrapper.set(ThumbsUp::getStatus, ThumbsUpConstant.UNLIKE);
            thumbsUpMapper.update(null, wrapper);

            IncrOrDecrColumnReq articleReq = IncrOrDecrColumnReq.newBuilder()
                    .setColumn("likes")
                    .setNum(1)
                    .setItemId(thumbsUp.getItemId())
                    .build();
            com.github.veerdone.yblog.cloud.base.api.article.ArticleInfo info = articleClientBlockingStub.incrOrDecrColumnAndGet(articleReq).getArticleInfo();
            ArticleInfo articleInfo = ArticleConvert.INSTANCE.toArticleInfo(info);

            IncrOrDecrColumnReq req = IncrOrDecrColumnReq.newBuilder()
                    .setNum(-1)
                    .setColumn("likes")
                    .setItemId(articleInfo.getUserId())
                    .build();
            userClientBlockingStub.incrOrDecrColumn(req);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ThumbsUpHandlerStrategyFactory.registerHandler(ThumbsUpConstant.ARTICLE, this);
    }
}
