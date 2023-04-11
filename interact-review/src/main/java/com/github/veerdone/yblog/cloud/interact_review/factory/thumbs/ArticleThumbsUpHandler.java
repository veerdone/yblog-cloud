package com.github.veerdone.yblog.cloud.interact_review.factory.thumbs;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.client.ArticleClient;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.Message;
import com.github.veerdone.yblog.cloud.base.model.ThumbsUp;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.common.constant.MessageConstant;
import com.github.veerdone.yblog.cloud.common.constant.ThumbsUpConstant;
import com.github.veerdone.yblog.cloud.interact_review.mapper.ThumbsUpMapper;
import com.github.veerdone.yblog.cloud.interact_review.service.MessageService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArticleThumbsUpHandler implements ThumbsUpHandler {
    private final ThumbsUpMapper thumbsUpMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    private final MessageService messageService;

    private ArticleClient articleClient;

    private UserClient userClient;

    public ArticleThumbsUpHandler(ThumbsUpMapper thumbsUpMapper, RedisTemplate<String, Object> redisTemplate,
                                  MessageService messageService) {
        this.thumbsUpMapper = thumbsUpMapper;
        this.redisTemplate = redisTemplate;
        this.messageService = messageService;
    }

    @DubboReference
    public void setUserClient(UserClient userClient) {
        this.userClient = userClient;
    }

    @DubboReference
    public void setArticleClient(ArticleClient articleClient) {
        this.articleClient = articleClient;
    }

    @Override
    public void save(ThumbsUp thumbsUp) {
        Long i = redisTemplate.opsForSet().add(CacheKey.USER_ARTICLE_THUMBS_UP + thumbsUp.getUserId(), thumbsUp.getItemId());
        if (i != null && i > 0L) {
            thumbsUp.setStatus(ThumbsUpConstant.LIKE);
            thumbsUpMapper.insert(thumbsUp);
            // 文章点赞加1
            ArticleInfo articleInfo = articleClient.incrOrDecrColumnAndGet(new IncrOrDecrColumnDto(thumbsUp.getItemId(), "likes", 1));
            // 用户点赞加1
            UserInfo userInfo = userClient.incrOrDecrColumnAndGet(new IncrOrDecrColumnDto(articleInfo.getUserId(), "likes", 1));

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

            ArticleInfo articleInfo = articleClient.incrOrDecrColumnAndGet(new IncrOrDecrColumnDto(thumbsUp.getItemId(), "likes", -1));
            userClient.incrOrDecrColumn(new IncrOrDecrColumnDto(articleInfo.getUserId(), "likes", -1));
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ThumbsUpHandlerStrategyFactory.registerHandler(ThumbsUpConstant.ARTICLE, this);
    }
}
