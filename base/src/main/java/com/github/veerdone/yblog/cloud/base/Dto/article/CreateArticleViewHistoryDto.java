package com.github.veerdone.yblog.cloud.base.Dto.article;

public class CreateArticleViewHistoryDto {
    private Long userId;

    private Long articleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
