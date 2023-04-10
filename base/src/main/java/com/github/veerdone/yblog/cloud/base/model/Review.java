package com.github.veerdone.yblog.cloud.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName("yblog_review")
public class Review implements Serializable {
    private static final long serialVersionUID = 43434312232142L;

    private Long id;

    private Long itemId;

    private Integer itemType;

    private Long userId;

    private Integer status;

    private Long reviewUserId;

    private String failReason;

    private Integer done;

    private Integer extra;

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

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
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

    public Long getReviewUserId() {
        return reviewUserId;
    }

    public void setReviewUserId(Long reviewUserId) {
        this.reviewUserId = reviewUserId;
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason;
    }

    public Integer getDone() {
        return done;
    }

    public void setDone(Integer done) {
        this.done = done;
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

    public Integer getExtra() {
        return extra;
    }

    public void setExtra(Integer extra) {
        this.extra = extra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Review review = (Review) o;
        return Objects.equals(id, review.id) && Objects.equals(itemId, review.itemId) && Objects.equals(itemType, review.itemType) && Objects.equals(userId, review.userId) && Objects.equals(status, review.status) && Objects.equals(reviewUserId, review.reviewUserId) && Objects.equals(failReason, review.failReason) && Objects.equals(done, review.done) && Objects.equals(extra, review.extra) && Objects.equals(createTime, review.createTime) && Objects.equals(updateTime, review.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemId, itemType, userId, status, reviewUserId, failReason, done, extra, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", itemType=" + itemType +
                ", userId=" + userId +
                ", status=" + status +
                ", reviewUserId=" + reviewUserId +
                ", failReason='" + failReason + '\'' +
                ", done=" + done +
                ", extra=" + extra +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
