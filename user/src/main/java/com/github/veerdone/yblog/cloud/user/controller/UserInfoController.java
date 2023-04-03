package com.github.veerdone.yblog.cloud.user.controller;


import com.github.veerdone.yblog.cloud.base.Dto.user.UpdateUserInfoDto;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}/_common")
    public UserInfo getById(@PathVariable("id") Long id) {
        return userInfoService.getById(id);
    }
}
