package com.github.veerdone.yblog.cloud.base.Dto.review;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class UpdateReviewDto {
    @NotNull(message = "id不能为空")
    private Long id;

    @NotNull(message = "item_id 不能为空")
    private Long itemId;

    @NotNull(message = "item_type 不能为空")
    private Long itemType;

    @NotNull(message = "user_id 不能为空")
    private Long userId;

    @NotNull(message = "review_user_id 不能为空")
    private Long reviewUserId;

    @Length(max = 1000, message = "审核失败最长为1000个字符")
    private String failReason;

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

    public Long getItemType() {
        return itemType;
    }

    public void setItemType(Long itemType) {
        this.itemType = itemType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
