package com.github.veerdone.yblog.cloud.comment.controller;

import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateReplyCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/comment/reply")
public class ReplyCommentController {
    private final ReplyCommentService replyCommentService;

    public ReplyCommentController(ReplyCommentService replyCommentService) {
        this.replyCommentService = replyCommentService;
    }

    @PostMapping
    public void create(@RequestBody @Validated CreateReplyCommentDto dto) {
        replyCommentService.create(dto);
    }

    @PutMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        replyCommentService.deleteById(id);
    }

    @GetMapping
    public List<ReplyCommentVo> list(@RequestParam("item_id") Long itemId,
                                           @RequestParam("comment_id") Long commentId,
                                           @RequestParam("type") Integer type) {

        return replyCommentService.list(itemId, commentId, type);
    }
}
