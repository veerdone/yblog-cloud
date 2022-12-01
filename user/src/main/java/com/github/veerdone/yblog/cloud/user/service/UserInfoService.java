package com.github.veerdone.yblog.cloud.user.service;

import com.github.veerdone.yblog.cloud.base.Dto.user.UpdateUserInfoDto;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;

import java.util.List;

public interface UserInfoService {
    UserInfo getUserInfoById(Long id);
    UserInfo create(UserInfo userInfo);

    List<UserInfo> getUserInfoByIds(List<Long> ids);

    void updateUserInfoById(UpdateUserInfoDto dto);
}
