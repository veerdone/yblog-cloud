package com.github.veerdone.yblog.cloud.user.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.veerdone.yblog.cloud.base.Dto.user.CreateUserDto;
import com.github.veerdone.yblog.cloud.base.Dto.user.LoginUserDto;
import com.github.veerdone.yblog.cloud.base.Vo.UserInfoVo;
import com.github.veerdone.yblog.cloud.user.service.UserDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserDataController {
    private static final Logger log = LoggerFactory.getLogger(UserDataController.class);

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
        UserInfoVo userInfoVo = userDataService.loginByPass(dto);
        log.info("user={} use pass login", StpUtil.getLoginId());
        return userInfoVo;
    }

    @PostMapping("/login/captcha")
    public UserInfoVo loginByCaptcha(@RequestBody @Validated LoginUserDto dto) {
        UserInfoVo userInfoVo = userDataService.loginByCaptcha(dto);
        log.info("user={} use captcha login", StpUtil.getLoginId());
        return userInfoVo;
    }
}
