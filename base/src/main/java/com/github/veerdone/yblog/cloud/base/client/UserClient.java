package com.github.veerdone.yblog.cloud.base.client;

import com.github.veerdone.yblog.cloud.base.exception.BizException;
import com.github.veerdone.yblog.cloud.base.model.UserData;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;

public interface UserClient {
    UserData getUserDataById(Long id) throws BizException;
}
