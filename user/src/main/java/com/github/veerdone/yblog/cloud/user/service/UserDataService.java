package com.github.veerdone.yblog.cloud.user.service;

import com.github.veerdone.yblog.cloud.base.Dto.user.CreateUserDto;
import com.github.veerdone.yblog.cloud.base.Vo.UserInfoVo;

public interface UserDataService {
    UserInfoVo register(CreateUserDto dto);
}
