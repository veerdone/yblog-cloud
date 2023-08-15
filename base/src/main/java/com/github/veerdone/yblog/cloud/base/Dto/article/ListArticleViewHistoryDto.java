package com.github.veerdone.yblog.cloud.base.Dto.article;

import com.github.veerdone.yblog.cloud.base.Order;
import com.github.veerdone.yblog.cloud.base.OrderMap;

import java.util.List;

public class ListArticleViewHistoryDto {
    private Long userId;

    private List<String> orders;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getOrders() {
        return orders;
    }

    public void setOrders(List<String> orders) {
        this.orders = orders;
    }

    public List<Order> getOrderList(String... columns) {
        OrderMap orderMap = new OrderMap(orders);
        return orderMap.orderList(columns);
    }
}
