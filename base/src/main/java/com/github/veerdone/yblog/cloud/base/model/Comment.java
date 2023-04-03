package com.github.veerdone.yblog.cloud.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName("yblog_comment")
public class Comment implements Serializable {
    private static final long serialVersionUID = 4343434231423423L;

    private Long id;

    private Long itemId;

    private Long userId;

    private String commentContent;

    private Integer deleted;

    private Integer likes;

    private Integer status;

    private Integer type;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(itemId, comment.itemId) && Objects.equals(userId, comment.userId) && Objects.equals(commentContent, comment.commentContent) && Objects.equals(deleted, comment.deleted) && Objects.equals(likes, comment.likes) && Objects.equals(status, comment.status) && Objects.equals(type, comment.type) && Objects.equals(createTime, comment.createTime) && Objects.equals(updateTime, comment.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, userId, commentContent, deleted, likes, status, type, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", userId=" + userId +
                ", commentContent='" + commentContent + '\'' +
                ", deleted=" + deleted +
                ", likes=" + likes +
                ", status=" + status +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
