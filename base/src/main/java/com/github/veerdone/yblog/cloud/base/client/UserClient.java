package com.github.veerdone.yblog.cloud.base.client;

import com.github.veerdone.yblog.cloud.base.model.UserInfo;

public interface UserClient {
    RpcResult<UserInfo> getUserInfoById(Long id);
}
