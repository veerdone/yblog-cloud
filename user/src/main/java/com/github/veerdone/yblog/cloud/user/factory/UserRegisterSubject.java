package com.github.veerdone.yblog.cloud.user.factory;

import com.github.veerdone.yblog.cloud.base.model.UserData;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.util.async.AsyncExecutor;

import java.util.ArrayList;
import java.util.List;

public class UserRegisterSubject {
    private static final List<UserRegisterObserver> handlerList = new ArrayList<>();

    public static void subscribe(UserRegisterObserver handler) {
        handlerList.add(handler);
    }

    public static void beforeHandler(UserData userData) {
        if (!handlerList.isEmpty()) {
            handlerList.forEach(handler -> handler.beforeRegister(userData));
        }
    }

    public static void asyncBeforeHandler(UserData userData) {
        AsyncExecutor.execute(() -> beforeHandler(userData));
    }

    public static void afterHandler(UserInfo userInfo) {
        if (!handlerList.isEmpty()) {
            handlerList.forEach(handler -> handler.afterRegisterUser(userInfo));
        }
    }

    public static void asyncAfterHandler(UserInfo userInfo) {
        AsyncExecutor.execute(() -> afterHandler(userInfo));
    }
}
