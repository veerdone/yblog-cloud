package com.github.veerdone.yblog.cloud.base.Dto.thumbsUp;

import javax.validation.constraints.NotNull;

public class ThumbsUpDto {
    @NotNull(message = "用户id 不能为空")
    private Long userId;

    @NotNull(message = "item_id 不能为空")
    private Long itemId;

    @NotNull(message = "item_type 不能为空")
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
