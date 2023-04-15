package com.github.veerdone.yblog.cloud.base.client;

import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;

import java.util.List;

public interface ArticleClient {
    ArticleInfo getById(Long id);

    List<ArticleInfo> listByIds(List<Long> ids);

    void updateById(ArticleInfo articleInfo);

    ArticleInfo updateStatusAndGet(Long id, Integer status);

    void incrOrDecrColumn(IncrOrDecrColumnDto dto);

    void updateStatusAndIncrOrDecrColumn(Long id, Integer status, IncrOrDecrColumnDto dto);

    ArticleInfo incrOrDecrColumnAndGet(IncrOrDecrColumnDto dto);
}
