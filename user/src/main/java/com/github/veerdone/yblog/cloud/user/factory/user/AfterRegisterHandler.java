package com.github.veerdone.yblog.cloud.user.factory.user;

import com.github.veerdone.yblog.cloud.base.model.UserInfo;

public interface AfterRegisterHandler {
    default void afterRegisterUser(UserInfo userInfo) {}
}
