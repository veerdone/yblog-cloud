package com.github.veerdone.yblog.cloud.article.service;

import com.github.veerdone.yblog.cloud.base.Dto.article.ListArticleViewHistoryDto;
import com.github.veerdone.yblog.cloud.base.Dto.article.UpdateArticleViewHistoryDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleViewHistoryVo;
import com.github.veerdone.yblog.cloud.base.model.ArticleViewHistory;

import java.util.List;

public interface ArticleViewHistoryService {
    void create(ArticleViewHistory articleViewHistory);

    void updateViewTime(UpdateArticleViewHistoryDto dto);

    List<ArticleViewHistoryVo> list(ListArticleViewHistoryDto dto);
}
