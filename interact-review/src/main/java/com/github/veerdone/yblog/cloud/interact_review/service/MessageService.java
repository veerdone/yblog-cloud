package com.github.veerdone.yblog.cloud.interact_review.service;

import com.github.veerdone.yblog.cloud.base.model.Message;

import java.util.Map;

public interface MessageService {
    void create(Message message);

    Map<String, Object> countNotRead(Long userId);
}
