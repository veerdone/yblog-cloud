package com.github.veerdone.yblog.cloud.interact_review.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.veerdone.yblog.cloud.base.KeyValue;
import com.github.veerdone.yblog.cloud.base.model.Message;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    List<KeyValue<String, Integer>> countNotReadGroupByMsgType(Long userId);
}
