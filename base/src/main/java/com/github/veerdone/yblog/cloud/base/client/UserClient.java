package com.github.veerdone.yblog.cloud.base.client;

import com.github.veerdone.yblog.cloud.base.model.UserInfo;

import java.util.List;

public interface UserClient {
    UserInfo getUserInfoById(Long id);

    List<UserInfo> getUserInfoByIds(List<Long> ids);
}
