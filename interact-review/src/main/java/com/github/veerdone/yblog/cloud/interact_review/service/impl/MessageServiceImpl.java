package com.github.veerdone.yblog.cloud.interact_review.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.KeyValue;
import com.github.veerdone.yblog.cloud.base.model.Message;
import com.github.veerdone.yblog.cloud.interact_review.mapper.MessageMapper;
import com.github.veerdone.yblog.cloud.interact_review.service.MessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageMapper messageMapper;

    @Override
    public void create(Message message) {
        messageMapper.insert(message);
    }

    @Override
    public Map<String, Object> countNotRead(Long userId) {
        List<KeyValue<String, Integer>> keyValueList = messageMapper.countNotReadGroupByMsgType(userId);
        Map<String, Integer> countNotReadGroupByMsgType = new HashMap<>(8);
        keyValueList.forEach(keyvalue -> countNotReadGroupByMsgType.put(keyvalue.getKey(), keyvalue.getValue()));
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Message::getStatus, 0)
                .eq(Message::getReceiverId, userId);
        Long total = messageMapper.selectCount(wrapper);
        Map<String, Object> map = new HashMap<>(3);
        map.put("count", countNotReadGroupByMsgType);
        map.put("total", total.intValue());
        return map;
    }
}
