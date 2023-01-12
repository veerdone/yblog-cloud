package com.github.veerdone.yblog.cloud.base.Dto.post;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UpdateArticleDto {
    @NotNull(message = "id不能为空")
    private Long id;

    private String title;

    private String content;

    private String description;

    private String coverPicture;

    @NotNull(message = "用户id不能为空")
    private Long userId;

    private List<Long> label;

    private Long classify;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
