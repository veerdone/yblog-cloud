package com.github.veerdone.yblog.cloud.user.api;

import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@DubboService
public class UserClientImpl implements UserClient {
    @Resource
    private UserInfoService userInfoService;

    @Override
    public UserInfo getUserInfoById(Long id) {
        return userInfoService.getUserInfoById(id);
    }
}
