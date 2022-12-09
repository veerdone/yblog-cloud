package com.github.veerdone.yblog.cloud.base.client;

import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;

public interface ArticleClient {
    ArticleInfo getById(Long id);

    void updateById(ArticleInfo articleInfo);

    void incrOrDecrColumn(IncrOrDecrColumnDto dto);
}
