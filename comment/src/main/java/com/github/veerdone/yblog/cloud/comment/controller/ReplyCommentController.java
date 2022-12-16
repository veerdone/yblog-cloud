package com.github.veerdone.yblog.cloud.comment.controller;

import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateReplyCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import com.github.veerdone.yblog.cloud.common.page.PageUtil;
import com.github.veerdone.yblog.cloud.common.response.result.ListResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/reply/comment")
public class ReplyCommentController {
    @Resource
    private ReplyCommentService replyCommentService;

    @PostMapping("/create")
    public void create(@RequestBody @Validated CreateReplyCommentDto dto) {
        replyCommentService.create(dto);
    }

    @GetMapping("/list")
    public ListResult<ReplyCommentVo> list(@RequestParam("item_id") Long itemId,
                                           @RequestParam("comment_id") Long commentId,
                                           @RequestParam("type") Integer type) {
        List<ReplyCommentVo> replyCommentVoList = replyCommentService.list(itemId, commentId, type);

        return PageUtil.response(replyCommentVoList);
    }
}
