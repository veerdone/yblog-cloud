package com.github.veerdone.yblog.cloud.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.veerdone.yblog.cloud.base.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {
}
