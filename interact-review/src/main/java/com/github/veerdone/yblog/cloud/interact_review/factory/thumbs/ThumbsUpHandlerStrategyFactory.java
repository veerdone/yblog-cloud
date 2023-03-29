package com.github.veerdone.yblog.cloud.interact_review.factory.thumbs;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class ThumbsUpHandlerStrategyFactory {
    private static final Map<Integer, ThumbsUpHandler> handlerMap = new HashMap<>();

    public static void registerHandler(Integer type, ThumbsUpHandler handler) {
        handlerMap.put(type, handler);
    }

    public static ThumbsUpHandler getHandler(Integer type) {
        ThumbsUpHandler handler = handlerMap.get(type);
        Assert.notNull(handler, StrUtil.format("can't not find {}, type is {}", ThumbsUpHandler.class, type));
        return handler;
    }
}
