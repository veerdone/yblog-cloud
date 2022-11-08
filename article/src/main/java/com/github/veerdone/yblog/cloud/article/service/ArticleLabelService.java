package com.github.veerdone.yblog.cloud.article.service;

import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;

import java.util.List;

public interface ArticleLabelService {
    void create(ArticleLabel articleLabel);

    List<ArticleLabel> listByClassifyId(Long classifyId);
}
