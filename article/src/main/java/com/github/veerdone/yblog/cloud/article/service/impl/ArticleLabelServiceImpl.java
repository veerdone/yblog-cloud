package com.github.veerdone.yblog.cloud.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleLabelMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        List<ArticleLabel> articleLabelList = new ArrayList<>(ids.size());
        ids.forEach(id -> articleLabelList.add(this.getById(id)));

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
