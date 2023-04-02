package com.github.veerdone.yblog.cloud.comment.controller;

import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateReplyCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment/reply")
public class ReplyCommentController {
    private final ReplyCommentService replyCommentService;

    public ReplyCommentController(ReplyCommentService replyCommentService) {
        this.replyCommentService = replyCommentService;
    }

    @PostMapping("/create")
    public void create(@RequestBody @Validated CreateReplyCommentDto dto) {
        replyCommentService.create(dto);
    }

    @GetMapping("/list/_common")
    public List<ReplyCommentVo> list(@RequestParam("item_id") Long itemId,
                                           @RequestParam("comment_id") Long commentId,
                                           @RequestParam("type") Integer type) {

        return replyCommentService.list(itemId, commentId, type);
    }
}
