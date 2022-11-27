package com.github.veerdone.yblog.cloud.base.Dto.comment;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ListQueryCommentDto {
    @NotNull(message = "itemId 不能为空")
    @Min(0)
    @Max(Long.MAX_VALUE)
    private Long itemId;

    @NotNull(message = "type 不能为空")
    @Min(0)
    @Max(Integer.MAX_VALUE)
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
