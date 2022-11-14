package com.github.veerdone.yblog.cloud.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.veerdone.yblog.cloud.base.ListTypeHandler;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@TableName(value = "yblog_article_info", autoResultMap = true)
public class ArticleInfo implements Serializable {
    private static final Long serialVersionUID = 43435345435454L;

    private Long id;

    private String title;

    private String description;

    private Integer views;

    private Integer collection;

    private Integer likes;

    private Integer status;

    private Integer comments;

    private String coverPicture;

    private Integer deleted;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    private Long userId;

    @TableField(typeHandler = ListTypeHandler.class)
    private List<Long> label;

    private Long classify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
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

    public List<Long> getLabel() {
        return label;
    }

    public void setLabel(List<Long> label) {
        this.label = label;
    }

    public Long getClassify() {
        return classify;
    }

    public void setClassify(Long classify) {
        this.classify = classify;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArticleInfo that = (ArticleInfo) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(views, that.views) && Objects.equals(collection, that.collection) && Objects.equals(likes, that.likes) && Objects.equals(status, that.status) && Objects.equals(comments, that.comments) && Objects.equals(coverPicture, that.coverPicture) && Objects.equals(deleted, that.deleted) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime) && Objects.equals(userId, that.userId) && Objects.equals(label, that.label) && Objects.equals(classify, that.classify);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, views, collection, likes, status, comments, coverPicture, deleted, createTime, updateTime, userId, label, classify);
    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", views=" + views +
                ", collection=" + collection +
                ", likes=" + likes +
                ", status=" + status +
                ", comments=" + comments +
                ", coverPicture='" + coverPicture + '\'' +
                ", deleted=" + deleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", userId=" + userId +
                ", label=" + label +
                ", classify=" + classify +
                '}';
    }
}
