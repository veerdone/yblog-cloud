package com.github.veerdone.yblog.cloud.base.Vo;

import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;

import java.util.List;

public class ArticleDetailVo {
    private Long id;

    private String title;

    private String content;

    private Integer views;

    private Integer collection;

    private Integer likes;

    private Integer status;

    private Integer comments;

    private Long userId;

    private Long createTime;

    private ArticleClassify articleClassify;

    private List<ArticleLabel> articleLabelList;

    private UserInfo userInfo;

    private boolean isLikes;

    private boolean isCollection;


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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
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

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public void setIsLikes(boolean isLikes) {
        this.isLikes = isLikes;
    }

    public boolean getIsLikes() {
        return this.isLikes;
    }

    public void setIsCollection(boolean isCollection) {
        this.isCollection = isCollection;
    }

    public boolean getIsCollection() {
        return this.isCollection;
    }
}
