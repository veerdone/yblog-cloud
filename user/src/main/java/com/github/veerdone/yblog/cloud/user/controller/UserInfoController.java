package com.github.veerdone.yblog.cloud.user.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.github.veerdone.yblog.cloud.base.Dto.user.UpdateUserInfoDto;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/info")
public class UserInfoController {
    private final UserInfoService userInfoService;

    public UserInfoController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @PutMapping
    public void updateById(@RequestBody @Validated UpdateUserInfoDto dto) {
        userInfoService.updateById(dto);
    }

    @GetMapping("/{id}")
    public UserInfo getById(@PathVariable("id") Long id) {
        return userInfoService.getById(id);
    }

    @GetMapping("/byToken")
    public UserInfo byToken() {
        long userId = StpUtil.getLoginIdAsLong();

        return userInfoService.getById(userId);
    }
}
