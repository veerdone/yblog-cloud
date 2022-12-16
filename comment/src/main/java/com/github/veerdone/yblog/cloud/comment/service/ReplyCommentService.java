package com.github.veerdone.yblog.cloud.comment.service;

import com.github.veerdone.yblog.cloud.base.Dto.comment.CreateReplyCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;

import java.util.List;

public interface ReplyCommentService {
    void create(CreateReplyCommentDto dto);

    void deleteByCommentId(Long commentId, Integer type);

    List<ReplyCommentVo> list(Long itemId, Long commentId, Integer type);
}
