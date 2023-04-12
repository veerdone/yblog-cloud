package com.github.veerdone.yblog.cloud.base.Dto.article;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UpdateArticleClassifyDto {
    @NotNull(message = "id 不能为空")
    private Long id;

    @NotEmpty(message = "分类名称不能为空")
    @Length(max = 20, message = "分类名称最多为20个字符")
    private String classifyName;

    @NotEmpty(message = "url不能为空")
    @Length(max = 20, message = "url 最多为20个字符")
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
