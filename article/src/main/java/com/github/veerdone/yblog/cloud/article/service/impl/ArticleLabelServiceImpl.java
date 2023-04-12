package com.github.veerdone.yblog.cloud.article.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleLabelMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ArticleLabelServiceImpl implements ArticleLabelService {
    private final ArticleLabelMapper articleLabelMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    public ArticleLabelServiceImpl(ArticleLabelMapper articleLabelMapper, RedisTemplate<String, Object> redisTemplate) {
        this.articleLabelMapper = articleLabelMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void create(ArticleLabel articleLabel) {
        articleLabelMapper.insert(articleLabel);
    }

    @Override
    public void updateById(ArticleLabel articleLabel) {
        articleLabelMapper.updateById(articleLabel);
    }

    @Override
    public ArticleLabel getById(Long id) {
        Object cache = redisTemplate.opsForHash().get(CacheKey.ARTICLE_LABEL_HASH, id);
        if (Objects.nonNull(cache)) {
            return (ArticleLabel) cache;
        }
        ArticleLabel articleLabel = articleLabelMapper.selectById(id);
        redisTemplate.opsForHash().put(CacheKey.ARTICLE_LABEL_HASH, id, articleLabel);

        return articleLabel;
    }

    @Override
    public List<ArticleLabel> getByIds(List<Long> ids) {
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(CacheKey.ARTICLE_LABEL_HASH);
        if (CollectionUtil.isNotEmpty(entries)) {
            List<ArticleLabel> articleLabelList = new ArrayList<>(entries.size());
            ids.forEach(id -> articleLabelList.add((ArticleLabel) entries.get(id)));

            return articleLabelList;
        }

        LambdaQueryWrapper<ArticleLabel> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ArticleLabel::getId, ids);

        List<ArticleLabel> articleLabelList = articleLabelMapper.selectList(wrapper);
        Map<Long, ArticleLabel> map = articleLabelList.stream()
                .collect(Collectors.toMap(ArticleLabel::getId, articleLabel -> articleLabel));
        redisTemplate.opsForHash().putAll(CacheKey.ARTICLE_LABEL_HASH, map);

        return articleLabelList;
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
