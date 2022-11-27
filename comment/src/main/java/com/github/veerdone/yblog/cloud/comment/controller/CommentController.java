package com.github.veerdone.yblog.cloud.comment.controller;

import com.github.veerdone.yblog.cloud.base.Dto.comment.ListQueryCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.CommentVo;
import com.github.veerdone.yblog.cloud.comment.service.CommentService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping("/list/query")
    public List<CommentVo> listByQuery(@RequestBody @Validated ListQueryCommentDto dto) {
        return commentService.listByQuery(dto);
    }
}
