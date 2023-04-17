package com.github.veerdone.yblog.cloud.comment.controller;

import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateCommentDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.QueryListCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.CommentVo;
import com.github.veerdone.yblog.cloud.comment.service.CommentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public void create(@RequestBody @Validated CreateCommentDto dto) {
        commentService.create(dto);
    }

    @PutMapping("/{id}")
    public void delete(@PathVariable Long id, @RequestParam("type") Integer type) {
        commentService.delete(id, type);
    }

    @PostMapping("/list")
    public List<CommentVo> listByQuery(@RequestBody @Validated QueryListCommentDto dto) {
        return commentService.listByQuery(dto);
    }
}
