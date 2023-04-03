package com.github.veerdone.yblog.cloud.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName("yblog_reply_comment")
public class ReplyComment implements Serializable {
    private static final long serialVersionUID = 2323534522343L;

    private Long id;

    private Long itemId;

    private Long commentId;

    private Long replyUserId;

    private String replyCommentContent;

    private String replyCommentId;

    private Long replyToUserId;

    private Integer likes;

    private Integer status;

    private Integer deleted;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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
        ReplyComment that = (ReplyComment) o;
        return Objects.equals(id, that.id) && Objects.equals(itemId, that.itemId) && Objects.equals(commentId, that.commentId) && Objects.equals(replyUserId, that.replyUserId) && Objects.equals(replyCommentContent, that.replyCommentContent) && Objects.equals(replyCommentId, that.replyCommentId) && Objects.equals(replyToUserId, that.replyToUserId) && Objects.equals(likes, that.likes) && Objects.equals(status, that.status) && Objects.equals(deleted, that.deleted) && Objects.equals(type, that.type) && Objects.equals(createTime, that.createTime) && Objects.equals(updateTime, that.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, commentId, replyUserId, replyCommentContent, replyCommentId, replyToUserId, likes, status, deleted, type, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "ReplyComment{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", commentId=" + commentId +
                ", replyUserId=" + replyUserId +
                ", replyCommentContent='" + replyCommentContent + '\'' +
                ", replyCommentId='" + replyCommentId + '\'' +
                ", replyToUserId=" + replyToUserId +
                ", likes=" + likes +
                ", status=" + status +
                ", deleted=" + deleted +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
