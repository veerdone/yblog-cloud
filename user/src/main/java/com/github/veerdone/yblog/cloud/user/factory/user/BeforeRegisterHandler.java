package com.github.veerdone.yblog.cloud.user.factory.user;

import com.github.veerdone.yblog.cloud.base.model.UserData;

public interface BeforeRegisterHandler {
    default void beforeRegister(UserData userData) {}
}
