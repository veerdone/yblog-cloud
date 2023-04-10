package com.github.veerdone.yblog.cloud.base.Dto.comment;

public class ReviewCommentDto {
    private Long itemId;

    private Integer itemType;

    private Long userId;

    private Integer extra;

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

    public Integer getExtra() {
        return extra;
    }

    public void setExtra(Integer extra) {
        this.extra = extra;
    }
}
