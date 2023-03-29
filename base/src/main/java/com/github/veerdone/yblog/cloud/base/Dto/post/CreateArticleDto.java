package com.github.veerdone.yblog.cloud.base.Dto.post;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CreateArticleDto {
    @NotNull(message = "用户id不能为空")
    @Min(value = 0)
    private Long userId;

    @NotBlank(message = "标题不能为空")
    @Length(max = 90, message = "标题最多30个字符")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    @Length(max = 200, message = "描述最多200个字符")
    private String description;

    @Length(max = 200, message = "封面图片最长为200个字符")
    private String coverPicture;

    @NotEmpty(message = "最少选择一个标签")
    @Size(min = 1, max = 3, message = "最少选择1个标签, 最多3个")
    private List<Long> label;

    @NotNull(message = "分类不能为空")
    private Long classify;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public List<Long> getLabel() {
        return label;
    }

    public void setLabel(List<Long> label) {
        this.label = label;
    }

    public Long getClassify() {
        return classify;
    }

    public void setClassify(Long classify) {
        this.classify = classify;
    }
}
