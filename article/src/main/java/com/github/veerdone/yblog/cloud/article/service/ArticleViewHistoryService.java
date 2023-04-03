package com.github.veerdone.yblog.cloud.article.service;

import com.github.veerdone.yblog.cloud.base.Vo.ArticleViewHistoryVo;
import com.github.veerdone.yblog.cloud.base.model.ArticleViewHistory;

import java.util.List;

public interface ArticleViewHistoryService {
    void create(ArticleViewHistory articleViewHistory);

    void updateById(Long id);

    List<ArticleViewHistoryVo> list();
}
