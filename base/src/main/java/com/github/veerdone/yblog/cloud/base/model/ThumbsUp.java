package com.github.veerdone.yblog.cloud.base.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName("yblog_thumbs_up")
public class ThumbsUp implements Serializable {
    private static final long serialVersionUID = 4343461434L;

    private Long id;

    private Long userId;

    private Long itemId;

    private Integer itemType;

    private Integer status;

    private Long createTime;

    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        ThumbsUp thumbsUp = (ThumbsUp) o;
        return Objects.equals(id, thumbsUp.id) && Objects.equals(userId, thumbsUp.userId) && Objects.equals(itemId, thumbsUp.itemId) && Objects.equals(itemType, thumbsUp.itemType) && Objects.equals(status, thumbsUp.status) && Objects.equals(createTime, thumbsUp.createTime) && Objects.equals(updateTime, thumbsUp.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, itemId, itemType, status, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "ThumbsUp{" +
                "id=" + id +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", itemType=" + itemType +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
