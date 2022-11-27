package com.github.veerdone.yblog.cloud.comment.service;

import com.github.veerdone.yblog.cloud.base.Dto.comment.ListQueryCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.CommentVo;

import java.util.List;

public interface CommentService {
    List<CommentVo> listByQuery(ListQueryCommentDto dto);
}
