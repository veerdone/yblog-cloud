package com.github.veerdone.yblog.cloud.user.service.impl;

import com.github.veerdone.yblog.cloud.user.mapper.UserInfoMapper;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

}
