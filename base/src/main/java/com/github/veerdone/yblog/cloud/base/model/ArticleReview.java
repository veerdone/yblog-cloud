package com.github.veerdone.yblog.cloud.base.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName("yblog_article_review")
public class ArticleReview implements Serializable {
    private static final long serialVersionUID = 3243354242343534L;

    private Long id;

    private Long articleId;

    private String articleTitle;

    private Long userId;

    private Integer status;

    private String failReason;

    private Integer deleted;

    private Long createTime;

    private Long updateTime;

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

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleReview that = (ArticleReview) o;
        return Objects.equals(id, that.id) && Objects.equals(articleId, that.articleId) && Objects.equals(articleTitle, that.articleTitle) && Objects.equals(userId, that.userId) && Objects.equals(status, that.status) && Objects.equals(failReason, that.failReason) && Objects.equals(deleted, that.deleted) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, articleId, articleTitle, userId, status, failReason, deleted, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "ArticleReview{" +
                "id=" + id +
                ", articleId=" + articleId +
                ", articleTitle='" + articleTitle + '\'' +
                ", userId=" + userId +
                ", status=" + status +
                ", failReason='" + failReason + '\'' +
                ", deleted=" + deleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
