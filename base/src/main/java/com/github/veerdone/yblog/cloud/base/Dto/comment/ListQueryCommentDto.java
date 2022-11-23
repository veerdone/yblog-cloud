package com.github.veerdone.yblog.cloud.base.Dto.comment;


public class ListQueryCommentDto {
    private Long itemId;

    private Integer type;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
