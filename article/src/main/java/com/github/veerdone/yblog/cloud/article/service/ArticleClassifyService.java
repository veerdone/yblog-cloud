package com.github.veerdone.yblog.cloud.article.service;

import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;

import java.util.List;

public interface ArticleClassifyService {
    void create(ArticleClassify articleClassify);

    List<ArticleClassify> list();
}
