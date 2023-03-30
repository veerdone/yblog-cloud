package com.github.veerdone.yblog.cloud.user.service;

import com.github.veerdone.yblog.cloud.base.Dto.user.CreateUserDto;
import com.github.veerdone.yblog.cloud.base.Dto.user.LoginUserDto;
import com.github.veerdone.yblog.cloud.base.Vo.UserInfoVo;
import com.github.veerdone.yblog.cloud.base.model.UserData;

public interface UserDataService {
    UserData getByEmail(String email);

    UserInfoVo register(CreateUserDto dto);

    UserInfoVo loginByCaptcha(LoginUserDto dto);

    UserInfoVo loginByPass(LoginUserDto dto);
}
