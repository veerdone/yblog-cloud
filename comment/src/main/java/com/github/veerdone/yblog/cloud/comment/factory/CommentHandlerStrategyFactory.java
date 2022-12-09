package com.github.veerdone.yblog.cloud.comment.factory;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class CommentHandlerStrategyFactory {
    private static final Map<Integer, CommentHandler> handlerMap = new HashMap<>(8);

    public static void registerHandler(Integer type, CommentHandler handler) {
        handlerMap.put(type, handler);
    }

    public static CommentHandler getHandler(Integer type) {
        CommentHandler handler = handlerMap.get(type);
        Assert.notNull(handler, StrUtil.format("can't not find {}, type is {}", CommentHandler.class, type));
        return handler;
    }
}
