package com.github.veerdone.yblog.cloud.base.Dto.article;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateArticleLabelDto {
    @NotEmpty(message = "标签名称不能为空")
    @Length(max = 20, message = "标签名称最长为20个字符")
    private String labelName;

    @NotNull(message = "分类id不能为空")
    @Min(value = 1, message = "分类id不能小于1")
    private Long classifyId;

    @NotEmpty(message = "url不能为空")
    @Length(max = 20, message = "url最长为20个字符")
    private String url;

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Long getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
