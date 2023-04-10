package com.github.veerdone.yblog.cloud.base.Dto.article;

import java.util.List;

public class QueryArticleInfoDto {
    private Long id;

    private String title;

    private Integer status;

    private Integer deleted;

    private Long userId;

    private List<Long> label;

    private Long classify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
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
