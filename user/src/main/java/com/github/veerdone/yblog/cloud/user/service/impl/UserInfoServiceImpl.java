package com.github.veerdone.yblog.cloud.user.service.impl;

import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.user.mapper.UserInfoMapper;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getUserInfoById(Long id) {
        return  userInfoMapper.selectById(id);
    }

    @Override
    public UserInfo create(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);

        return this.getUserInfoById(userInfo.getId());
    }
}
