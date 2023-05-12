package com.github.veerdone.yblog.cloud.user.factory;

import com.github.veerdone.yblog.cloud.base.model.UserInfo;

public interface AfterRegisterObServer {
    default void afterRegisterUser(UserInfo userInfo) {}
}
