package com.github.veerdone.yblog.cloud.comment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.comment.ListQueryCommentDto;
import com.github.veerdone.yblog.cloud.base.Vo.CommentVo;
import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.convert.CommentConvert;
import com.github.veerdone.yblog.cloud.base.model.Comment;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.comment.mapper.CommentMapper;
import com.github.veerdone.yblog.cloud.comment.service.CommentService;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private ReplyCommentService replyCommentService;

    @DubboReference
    private UserClient userClient;

    @Override
    public List<CommentVo> listByComment(ListQueryCommentDto dto) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getItemId, dto.getItemId())
                .eq(Comment::getType, dto.getType())
                .eq(Comment::getStatus, 1);
        List<Comment> commentList = commentMapper.selectList(wrapper);
        if (CollectionUtil.isEmpty(commentList)) {
            return Collections.emptyList();
        }

        List<CommentVo> commentVoList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentVo commentVo = CommentConvert.INSTANCE.toVo(comment);
            UserInfo userInfo = userClient.getUserInfoById(comment.getUserId());
            commentVo.setUserInfo(userInfo);
            List<ReplyCommentVo> replyCommentVoList = replyCommentService.list(comment.getItemId(), comment.getId());
            commentVo.setReplyCommentList(replyCommentVoList);
            commentVoList.add(commentVo);
        }

        return commentVoList;
    }
}
