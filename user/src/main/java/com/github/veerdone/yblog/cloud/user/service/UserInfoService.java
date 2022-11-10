package com.github.veerdone.yblog.cloud.user.service;

import com.github.veerdone.yblog.cloud.base.model.UserInfo;

public interface UserInfoService {
    UserInfo getUserInfoById(Long id);
    UserInfo create(UserInfo userInfo);
}
