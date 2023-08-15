package com.github.veerdone.yblog.cloud.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderMap {
    private final Map<String, String> map;

    public OrderMap(List<String> orders) {
        if (orders.isEmpty()) {
            map = Collections.emptyMap();
            return;
        }

        map = new HashMap<>(orders.size());
        orders.forEach(order -> {
            String[] strings = order.split(":");
            if (strings.length == 2) {
                String key = strings[0];
                String value = check(strings[1]);
                map.put(key, value);
            }
        });
    }

    public List<Order> orderList() {
        if (map.isEmpty()) {
            return Collections.emptyList();
        }
        List<Order> list = new ArrayList<>(map.size());
        map.forEach((key, value) -> list.add(new Order(key, value)));

        return list;
    }

    public List<Order> orderList(String... columns) {
        if (map.isEmpty()) {
            return Collections.emptyList();
        }
        List<Order> list = columns.length == 0 ? new ArrayList<>(map.size()) : new ArrayList<>(columns.length);
        List<String> columnList = Arrays.asList(columns);
        map.forEach((key, value) -> {
            if (columnList.contains(key)) {
                list.add(new Order(key, value));
            }
        });

        return list;
    }

    private String check(String s) {
        if ("desc".equalsIgnoreCase(s)) {
            return s;
        } else if ("asc".equalsIgnoreCase(s)) {
            return s;
        } else {
            return "asc";
        }
    }
}
