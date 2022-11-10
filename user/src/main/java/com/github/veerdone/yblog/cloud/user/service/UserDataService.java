package com.github.veerdone.yblog.cloud.user.service;

import com.github.veerdone.yblog.cloud.base.Dto.user.CreateUserDto;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;

public interface UserDataService {
    UserInfo create(CreateUserDto dto);
}
