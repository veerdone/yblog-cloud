package com.github.veerdone.yblog.cloud.comment.service;

import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateCommentDto;
import com.github.veerdone.yblog.cloud.base.Dto.comment.QueryListCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.CommentVo;
import com.github.veerdone.yblog.cloud.base.model.Comment;

import java.util.List;

public interface CommentService {

    void create(CreateCommentDto dto);
    void delete(Long id, Integer type);

    Comment getById(Long id);

    void updateStatus(Long id, Integer type, Integer status);

    List<CommentVo> listByQuery(QueryListCommentDto dto);
}
