package com.github.veerdone.yblog.cloud.base.Dto.article;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

public class CreateArticleClassifyDto {
    @NotEmpty(message = "分类名称不能为空")
    @Length(max = 20, message = "分类名称最多为20个字符")
    private String classifyName;

    @NotEmpty(message = "url不能为空")
    @Length(max = 20, message = "url 最多为20个字符")
    private String url;

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
