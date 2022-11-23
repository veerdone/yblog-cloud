package com.github.veerdone.yblog.cloud.comment.controller;

import com.github.veerdone.yblog.cloud.comment.service.CommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;
}
