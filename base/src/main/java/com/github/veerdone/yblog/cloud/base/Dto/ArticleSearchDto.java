package com.github.veerdone.yblog.cloud.base.Dto;

public class ArticleSearchDto {
    private Integer from = 0;

    private Integer size = 10;

    private String keyword;

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
