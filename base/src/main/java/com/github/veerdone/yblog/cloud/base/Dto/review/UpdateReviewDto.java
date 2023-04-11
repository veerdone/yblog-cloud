package com.github.veerdone.yblog.cloud.base.Dto.review;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class UpdateReviewDto {
    @NotNull(message = "id 不能为空")
    private Long id;

    @NotNull(message = "审核人 id 不能为空")
    private Long reviewUserId;

    @Length(max = 1000, message = "审核失败最长为 1000 个字符")
    private String failReason;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
