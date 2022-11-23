package com.github.veerdone.yblog.cloud.base.Vo;

import com.github.veerdone.yblog.cloud.base.model.UserInfo;

public class ReplyCommentVo {
    private Long id;

    private Long itemId;

    private Long commentId;

    private Long replyUserId;

    private String replyCommentContent;

    private String replyCommentId;

    private Long replyToUserId;

    private Integer likes;

    private Integer type;

    private Long createTime;

    private Long updateTime;

    private UserInfo replyUserInfo;

    private UserInfo replyToUserInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getReplyUserId() {
        return replyUserId;
    }

    public void setReplyUserId(Long replyUserId) {
        this.replyUserId = replyUserId;
    }

    public String getReplyCommentContent() {
        return replyCommentContent;
    }

    public void setReplyCommentContent(String replyCommentContent) {
        this.replyCommentContent = replyCommentContent;
    }

    public String getReplyCommentId() {
        return replyCommentId;
    }

    public void setReplyCommentId(String replyCommentId) {
        this.replyCommentId = replyCommentId;
    }

    public Long getReplyToUserId() {
        return replyToUserId;
    }

    public void setReplyToUserId(Long replyToUserId) {
        this.replyToUserId = replyToUserId;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public UserInfo getReplyUserInfo() {
        return replyUserInfo;
    }

    public void setReplyUserInfo(UserInfo replyUserInfo) {
        this.replyUserInfo = replyUserInfo;
    }

    public UserInfo getReplyToUserInfo() {
        return replyToUserInfo;
    }

    public void setReplyToUserInfo(UserInfo replyToUserInfo) {
        this.replyToUserInfo = replyToUserInfo;
    }
}
