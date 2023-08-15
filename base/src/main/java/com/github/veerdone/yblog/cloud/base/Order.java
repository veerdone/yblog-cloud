package com.github.veerdone.yblog.cloud.base;

public class Order {
    private String column;

    private String orderType;

    public Order(String column, String orderType) {
        this.column = column;
        this.orderType = orderType;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
