package com.github.veerdone.yblog.cloud.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleLabelMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ArticleLabelServiceImpl implements ArticleLabelService {
    @Resource
    private ArticleLabelMapper articleLabelMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void create(ArticleLabel articleLabel) {
        articleLabelMapper.insert(articleLabel);
    }

    @Override
    public ArticleLabel getById(Long id) {
        String key = CacheKey.ARTICLE_LABEL_QUERY_BY_ID + id;
        Object cache = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(cache)) {
            return (ArticleLabel) cache;
        }
        ArticleLabel articleLabel = articleLabelMapper.selectById(id);
        redisTemplate.opsForValue().set(key, articleLabel, 30, TimeUnit.MINUTES);

        return articleLabel;
    }

    @Override
    public List<ArticleLabel> listByClassifyId(Long classifyId) {
        if (Objects.isNull(classifyId) || classifyId <= 0) {
            return articleLabelMapper.selectList(null);
        }
        LambdaQueryWrapper<ArticleLabel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleLabel::getClassifyId, classifyId);

        return articleLabelMapper.selectList(wrapper);
    }
}
