package com.github.veerdone.yblog.cloud.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleLabelMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleLabelServiceImpl implements ArticleLabelService {
    @Resource
    private ArticleLabelMapper articleLabelMapper;

    @Override
    public void create(ArticleLabel articleLabel) {
        articleLabelMapper.insert(articleLabel);
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
