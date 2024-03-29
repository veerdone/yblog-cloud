package com.github.veerdone.yblog.cloud.common.web.result;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

public class ListResult<T> extends BaseResult {
    private List<T> list;

    @JsonSerialize(using = LocalLongSerialize.class)
    private Long total;

    private Boolean hasMore;

    public ListResult() {
    }

    public ListResult(int code, String message, List<T> list, long total) {
        super(code, message);
        this.list = list;
        this.total = total;
    }

    public ListResult(int code, String message, List<T> list, long total, Boolean hasMore) {
        super(code, message);
        this.list = list;
        this.total = total;
        this.hasMore = hasMore;
    }

    public ListResult(int code, String message, String errCode, List<T> list, Long total) {
        super(code, message, errCode);
        this.list = list;
        this.total = total;
    }

    public static <T> ListResult<T> result(List<T> t, Long total) {
        return new ListResult<>(0, "ok", t, total);
    }

    public static <T> ListResult<T> result(List<T> t, Long total, boolean hasMore) {
        return new ListResult<>(0, "ok", t, total, hasMore);
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }
}
