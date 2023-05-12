package com.github.veerdone.yblog.cloud.user.factory;

import com.github.veerdone.yblog.cloud.base.model.UserData;

public interface BeforeRegisterObserver {
    default void beforeRegister(UserData userData) {}
}
