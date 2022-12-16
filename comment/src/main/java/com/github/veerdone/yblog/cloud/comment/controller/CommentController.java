package com.github.veerdone.yblog.cloud.comment.controller;

import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateCommentDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.ListQueryCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.CommentVo;
import com.github.veerdone.yblog.cloud.comment.service.CommentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping("create")
    public void create(@RequestBody @Validated CreateCommentDto dto) {
        commentService.create(dto);
    }

    @PutMapping("/delete")
    public void delete(@RequestParam("item_id") Long itemId, @RequestParam("type") Integer type) {
        commentService.delete(itemId, type);
    }

    @PostMapping("/list/query")
    public List<CommentVo> listByQuery(@RequestBody @Validated ListQueryCommentDto dto) {
        return commentService.listByQuery(dto);
    }
}
