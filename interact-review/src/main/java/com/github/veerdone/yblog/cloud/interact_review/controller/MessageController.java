package com.github.veerdone.yblog.cloud.interact_review.controller;

import com.github.veerdone.yblog.cloud.interact_review.service.MessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
public class MessageController {
    @Resource
    private MessageService messageService;

    @GetMapping("/message/count_not_read/{user_id}")
    public Map<String, Object> countNotRead(@PathVariable("user_id") Long userId) {
        return messageService.countNotRead(userId);
    }
}
