package com.github.veerdone.yblog.cloud.base.client;

import com.github.veerdone.yblog.cloud.base.exception.BizException;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;

public interface UserClient {
    UserInfo getUserInfoById(Long id) throws BizException;
}
