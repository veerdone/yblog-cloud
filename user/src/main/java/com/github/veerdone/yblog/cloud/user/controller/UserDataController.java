package com.github.veerdone.yblog.cloud.user.controller;

import com.github.veerdone.yblog.cloud.base.Dto.user.CreateUserDto;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.user.service.UserDataService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserDataController {
    @Resource
    private UserDataService userDataService;

    @PostMapping("/create")
    public UserInfo createUser(@RequestBody @Validated CreateUserDto dto) {
        return userDataService.create(dto);
    }
}
