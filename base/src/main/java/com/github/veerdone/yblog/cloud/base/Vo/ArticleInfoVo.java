package com.github.veerdone.yblog.cloud.base.Vo;

import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;

import java.util.List;
import java.util.Objects;

public class ArticleInfoVo {
    private Long id;

    private String title;

    private String description;

    private Integer views;

    private Long userId;

    private Integer collection;

    private Integer likes;

    private Integer comments;

    private String coverPicture;

    private Long createTime;

    private UserInfo userInfo;

    private List<Long> label;

    private Long classify;

    private ArticleClassify articleClassify;

    private List<ArticleLabel> articleLabelList;

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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
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

    public ArticleClassify getArticleClassify() {
        return articleClassify;
    }

    public void setArticleClassify(ArticleClassify articleClassify) {
        this.articleClassify = articleClassify;
    }

    public List<ArticleLabel> getArticleLabelList() {
        return articleLabelList;
    }

    public void setArticleLabelList(List<ArticleLabel> articleLabelList) {
        this.articleLabelList = articleLabelList;
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
        ArticleInfoVo that = (ArticleInfoVo) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(views, that.views) && Objects.equals(userId, that.userId) && Objects.equals(collection, that.collection) && Objects.equals(likes, that.likes) && Objects.equals(comments, that.comments) && Objects.equals(coverPicture, that.coverPicture) && Objects.equals(createTime, that.createTime) && Objects.equals(userInfo, that.userInfo) && Objects.equals(label, that.label) && Objects.equals(classify, that.classify) && Objects.equals(articleClassify, that.articleClassify) && Objects.equals(articleLabelList, that.articleLabelList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, views, userId, collection, likes, comments, coverPicture, createTime, userInfo, label, classify, articleClassify, articleLabelList);
    }

    @Override
    public String toString() {
        return "ArticleInfoVo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", views=" + views +
                ", userId=" + userId +
                ", collection=" + collection +
                ", likes=" + likes +
                ", comments=" + comments +
                ", coverPicture='" + coverPicture + '\'' +
                ", createTime=" + createTime +
                ", userInfo=" + userInfo +
                ", label=" + label +
                ", classify=" + classify +
                ", articleClassify=" + articleClassify +
                ", articleLabelList=" + articleLabelList +
                '}';
    }
}
