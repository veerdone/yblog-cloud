package com.github.veerdone.yblog.cloud.user.factory;

import org.springframework.beans.factory.InitializingBean;

public interface UserRegisterObserver extends BeforeRegisterObserver, AfterRegisterObServer, InitializingBean {
    @Override
    default void afterPropertiesSet() throws Exception {
        UserRegisterSubject.subscribe(this);
    }
}
