package com.github.veerdone.yblog.cloud.comment.service;

import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;

import java.util.List;

public interface ReplyCommentService {

    List<ReplyCommentVo> list(Long itemId, Long commentId);
}
