package com.github.veerdone.yblog.cloud.base.Dto;

import java.io.Serializable;

public class IncrOrDecrColumnDto implements Serializable {
    private static final Long serialVersionUID = 434343242342343224L;

    private Long itemId;

    private String column;

    private Integer num;

    public IncrOrDecrColumnDto() {
    }

    public IncrOrDecrColumnDto(Long itemId, String column, Integer num) {
        this.itemId = itemId;
        this.column = column;
        this.num = num;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
