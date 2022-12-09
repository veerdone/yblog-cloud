package com.github.veerdone.yblog.cloud.comment.factory;

import com.github.veerdone.yblog.cloud.common.constant.CommentConstant;
import org.springframework.stereotype.Component;

@Component
public class ArticleCommentHandler implements CommentHandler {
    @Override
    public void createComment() {

    }

    @Override
    public void createReplyComment() {

    }

    @Override
    public int getType() {
        return CommentConstant.ARTICLE;
    }
}
