package com.github.veerdone.yblog.cloud.base.Dto.thumbsUp;

public class ThumbsUpDto {
    private Long userId;

    private Long itemId;

    private Long itemType;

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

    public Long getItemType() {
        return itemType;
    }

    public void setItemType(Long itemType) {
        this.itemType = itemType;
    }
}
