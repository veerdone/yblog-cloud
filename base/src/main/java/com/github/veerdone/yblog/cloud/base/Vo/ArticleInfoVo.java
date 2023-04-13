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

    private boolean isLike;

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

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public boolean getIsLike() {
        return this.isLike;
    }
}
