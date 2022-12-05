package com.github.veerdone.yblog.cloud.base.client;

import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;

public interface ArticleClient {
    void updateById(ArticleInfo articleInfo);
}
