package com.github.veerdone.yblog.cloud.article.service.impl;

import com.github.veerdone.yblog.cloud.article.mapper.ArticleClassifyMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleClassifyService;
import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleClassifyServiceImpl implements ArticleClassifyService {
    private final ArticleClassifyMapper articleClassifyMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    public ArticleClassifyServiceImpl(ArticleClassifyMapper articleClassifyMapper, RedisTemplate<String, Object> redisTemplate) {
        this.articleClassifyMapper = articleClassifyMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void create(ArticleClassify articleClassify) {
        articleClassifyMapper.insert(articleClassify);
    }

    @Override
    public void updateById(ArticleClassify articleClassify) {
        articleClassifyMapper.updateById(articleClassify);
    }

    @Override
    public ArticleClassify getById(Long id) {
        String key = CacheKey.ARTICLE_CLASSIFY_QUERY_BY_ID + id;
        Object cache = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(cache)) {
            return (ArticleClassify) cache;
        }
        ArticleClassify articleClassify = articleClassifyMapper.selectById(id);
        redisTemplate.opsForValue().set(key, articleClassify, 30, TimeUnit.MINUTES);

        return articleClassify;
    }

    @Override
    public List<ArticleClassify> list() {
        return articleClassifyMapper.selectList(null);
    }
}
