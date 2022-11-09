package com.github.veerdone.yblog.cloud.user.service.impl;

import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.exception.BizException;
import com.github.veerdone.yblog.cloud.base.model.UserData;
import com.github.veerdone.yblog.cloud.user.mapper.UserDataMapper;
import com.github.veerdone.yblog.cloud.user.service.UserDataService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
@DubboService(interfaceClass = UserClient.class)
public class UserDataServiceImpl implements UserDataService, UserClient {
    @Resource
    private UserDataMapper userDataMapper;

    @Override
    public UserData getUserDataById(Long id) throws BizException {
        return userDataMapper.selectById(id);
    }
}
