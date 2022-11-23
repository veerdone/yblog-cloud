package com.github.veerdone.yblog.cloud.comment.controller;

import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/reply/comment")
public class ReplyCommentController {
    @Resource
    private ReplyCommentService replyCommentService;
}
