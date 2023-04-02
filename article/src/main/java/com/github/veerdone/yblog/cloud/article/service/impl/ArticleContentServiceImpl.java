package com.github.veerdone.yblog.cloud.article.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleContentMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleContentService;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.article.service.MqProvider;
import com.github.veerdone.yblog.cloud.base.Dto.post.CreateArticleDto;
import com.github.veerdone.yblog.cloud.base.Dto.post.UpdateArticleDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleContent;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
public class ArticleContentServiceImpl implements ArticleContentService {
    private static final Logger log = LoggerFactory.getLogger(ArticleContentServiceImpl.class);


    private final ArticleContentMapper articleContentMapper;

    private final ArticleInfoService articleInfoService;

    private final TransactionTemplate transactionTemplate;

    private final RedisTemplate<String, Object> redisTemplate;

    private final MqProvider mqProvider;

    public ArticleContentServiceImpl(ArticleContentMapper articleContentMapper, ArticleInfoService articleInfoService,
                                     TransactionTemplate transactionTemplate, RedisTemplate<String, Object> redisTemplate,
                                     MqProvider mqProvider) {
        this.articleContentMapper = articleContentMapper;
        this.articleInfoService = articleInfoService;
        this.transactionTemplate = transactionTemplate;
        this.redisTemplate = redisTemplate;
        this.mqProvider = mqProvider;
    }

    @Override
    public void create(CreateArticleDto dto) {
        ArticleInfo articleInfo = ArticleConvert.INSTANCE.toArticleInfo(dto);
        transactionTemplate.executeWithoutResult(status -> {
            ArticleContent articleContent = new ArticleContent();
            articleContent.setContent(dto.getContent());
            articleContentMapper.insert(articleContent);

            articleInfo.setId(articleContent.getId());
            articleInfo.setCreateTime(articleContent.getCreateTime());
            articleInfo.setUpdateTime(articleContent.getUpdateTime());
            articleInfoService.create(articleInfo);
        });

        mqProvider.reviewArticle(articleInfo);
    }

    @Override
    public ArticleContent getById(Long id) {
        String cacheKey = CacheKey.ARTICLE_CONTENT_QUERY_BY_ID + id;
        log.debug("set article_content cache by cache_key={}", cacheKey);
        Object cache = redisTemplate.opsForValue().get(cacheKey);
        if (Objects.nonNull(cache)) {
            return (ArticleContent) cache;
        }
        ArticleContent articleContent = articleContentMapper.selectById(id);
        redisTemplate.opsForValue().set(cacheKey, articleContent, 30, TimeUnit.MINUTES);

        return articleContent;
    }

    @Override
    public void updateById(UpdateArticleDto dto) {
        ArticleInfo articleInfo = ArticleConvert.INSTANCE.toArticleInfo(dto);
        transactionTemplate.executeWithoutResult(status -> {
            articleInfoService.updateById(articleInfo);

            if (StrUtil.isNotBlank(dto.getContent())) {
                ArticleContent articleContent = new ArticleContent();
                articleContent.setId(dto.getId());
                articleContent.setContent(dto.getContent());

                articleContentMapper.updateById(articleContent);
            }
        });

        mqProvider.reviewArticle(articleInfo);
        String cacheKey = CacheKey.ARTICLE_CONTENT_QUERY_BY_ID + dto.getId();
        log.debug("del article_content cache by cache_key={}", cacheKey);
        redisTemplate.delete(cacheKey);
    }

    @Override
    public ArticleDetailVo getArticleDetailVoById(Long id) {
        ArticleDetailVo articleDetailVo = articleInfoService.getArticleDetailVoById(id);
        ArticleContent articleContent = this.getById(id);
        articleDetailVo.setContent(articleContent.getContent());

        return articleDetailVo;
    }
}
