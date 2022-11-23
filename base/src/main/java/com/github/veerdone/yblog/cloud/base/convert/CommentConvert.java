package com.github.veerdone.yblog.cloud.base.convert;

import com.github.veerdone.yblog.cloud.base.Vo.CommentVo;
import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;
import com.github.veerdone.yblog.cloud.base.model.Comment;
import com.github.veerdone.yblog.cloud.base.model.ReplyComment;
import org.mapstruct.factory.Mappers;

public interface CommentConvert {
    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

    CommentVo toVo(Comment comment);

    ReplyCommentVo toVo(ReplyComment replyComment);
}
