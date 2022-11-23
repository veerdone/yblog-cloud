package com.github.veerdone.yblog.cloud.base.Vo;


import com.github.veerdone.yblog.cloud.base.model.UserInfo;

import java.util.List;

public class CommentVo {
    private Long id;

    private String commentContent;

    private Long itemId;

    private Long userId;

    private Integer likes;

    private Long createTime;

    private Long updateTime;

    private List<ReplyCommentVo> replyCommentList;

    private UserInfo userInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
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

    public List<ReplyCommentVo> getReplyCommentList() {
        return replyCommentList;
    }

    public void setReplyCommentList(List<ReplyCommentVo> replyCommentList) {
        this.replyCommentList = replyCommentList;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
