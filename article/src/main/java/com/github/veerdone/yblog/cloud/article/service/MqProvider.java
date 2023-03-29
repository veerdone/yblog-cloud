package com.github.veerdone.yblog.cloud.article.service;

import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;

public interface MqProvider {
    void reviewArticle(ArticleInfo articleInfo);
}
