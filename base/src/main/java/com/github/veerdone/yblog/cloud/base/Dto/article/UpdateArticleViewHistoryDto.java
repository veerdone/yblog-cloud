package com.github.veerdone.yblog.cloud.base.Dto.article;

import javax.validation.constraints.NotNull;

public class UpdateArticleViewHistoryDto {
    @NotNull(message = "文章id不能为空")
    private Long articleId;

    @NotNull(message = "用户id不能为空")
    private Long userId;

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
