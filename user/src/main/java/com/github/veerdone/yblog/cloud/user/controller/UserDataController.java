package com.github.veerdone.yblog.cloud.user.controller;

import com.github.veerdone.yblog.cloud.base.Dto.user.CreateUserDto;
import com.github.veerdone.yblog.cloud.base.Dto.user.LoginUserDto;
import com.github.veerdone.yblog.cloud.base.Vo.UserInfoVo;
import com.github.veerdone.yblog.cloud.user.service.UserDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserDataController {
    private final UserDataService userDataService;

    public UserDataController(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    @PostMapping("/register")
    public UserInfoVo createUser(@RequestBody @Validated CreateUserDto dto) {
        return userDataService.register(dto);
    }

    @PostMapping("/login/pass")
    public UserInfoVo loginByPass(@RequestBody @Validated LoginUserDto dto) {
        return userDataService.loginByPass(dto);
    }

    @PostMapping("/login/captcha")
    public UserInfoVo loginByCaptcha(@RequestBody @Validated LoginUserDto dto) {
        return userDataService.loginByCaptcha(dto);
    }
}
