package com.github.veerdone.yblog.cloud.user.controller;


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

import javax.annotation.Resource;

@RestController
@RequestMapping("/user/info")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    @PutMapping("/update")
    public void updateUserInfoById(@RequestBody @Validated UpdateUserInfoDto dto) {
        userInfoService.updateUserInfoById(dto);
    }

    @GetMapping("/{id}")
    public UserInfo getById(@PathVariable("id") Long id) {
        return userInfoService.getUserInfoById(id);
    }
}
