package com.github.veerdone.yblog.cloud.base.Vo;

import com.github.veerdone.yblog.cloud.base.model.ArticleViewHistory;

public class ArticleViewHistoryVo {
    private ArticleInfoVo articleInfoVo;

    private ArticleViewHistory articleViewHistory;

    public ArticleInfoVo getArticleInfoVo() {
        return articleInfoVo;
    }

    public void setArticleInfoVo(ArticleInfoVo articleInfoVo) {
        this.articleInfoVo = articleInfoVo;
    }

    public ArticleViewHistory getArticleViewHistory() {
        return articleViewHistory;
    }

    public void setArticleViewHistory(ArticleViewHistory articleViewHistory) {
        this.articleViewHistory = articleViewHistory;
    }
}
