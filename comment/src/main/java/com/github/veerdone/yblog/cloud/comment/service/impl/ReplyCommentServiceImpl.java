package com.github.veerdone.yblog.cloud.comment.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.Vo.ReplyCommentVo;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.convert.CommentConvert;
import com.github.veerdone.yblog.cloud.base.model.ReplyComment;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.comment.mapper.ReplyCommentMapper;
import com.github.veerdone.yblog.cloud.comment.service.ReplyCommentService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReplyCommentServiceImpl implements ReplyCommentService {
    @Resource
    private ReplyCommentMapper replyCommentMapper;

    @DubboReference
    private UserClient userClient;

    @Override
    public List<ReplyCommentVo> list(Long itemId, Long commentId) {
        LambdaQueryWrapper<ReplyComment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ReplyComment::getItemId, itemId)
                .eq(ReplyComment::getCommentId, commentId)
                .eq(ReplyComment::getStatus, 1);
        List<ReplyComment> replyCommentList = replyCommentMapper.selectList(wrapper);
        if (CollectionUtil.isEmpty(replyCommentList)) {
            return Collections.emptyList();
        }

        List<ReplyCommentVo> replyCommentVoList = new ArrayList<>();
        for (ReplyComment replyComment : replyCommentList) {
            ReplyCommentVo replyCommentVo = CommentConvert.INSTANCE.toVo(replyComment);
            UserInfo replyUserInfo = userClient.getUserInfoById(replyCommentVo.getReplyUserId());
            replyCommentVo.setReplyUserInfo(replyUserInfo);
            UserInfo replyToUserInfo = userClient.getUserInfoById(replyCommentVo.getReplyToUserId());
            replyCommentVo.setReplyToUserInfo(replyToUserInfo);
            replyCommentVoList.add(replyCommentVo);
        }

        return replyCommentVoList;
    }
}
