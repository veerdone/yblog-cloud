package com.github.veerdone.yblog.cloud.base.Dto.review;

import javax.validation.constraints.NotNull;

public class ArticleReviewThroughDto {
    @NotNull(message = "id 不能为空")
    private Long id;

    @NotNull(message = "article_id 不能为空")
    private Long articleId;

    @NotNull(message = "user_id 不能为空")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
