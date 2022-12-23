package com.github.veerdone.yblog.cloud.user.factory.user;

import com.github.veerdone.yblog.cloud.base.model.UserData;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import org.springframework.beans.factory.InitializingBean;

public interface UserRegisterHandler extends BeforeRegisterHandler, AfterRegisterHandler, InitializingBean {
    @Override
    default void afterRegisterUser(UserInfo userInfo) {}

    @Override
    default void beforeRegister(UserData userData) {}

    @Override
    default void afterPropertiesSet() throws Exception {
        UserRegisterFactory.addHandler(this);
    }
}
