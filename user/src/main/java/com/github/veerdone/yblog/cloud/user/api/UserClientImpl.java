package com.github.veerdone.yblog.cloud.user.api;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DubboService
public class UserClientImpl implements UserClient {
    private static final Logger log = LoggerFactory.getLogger(UserClientImpl.class);

    private final UserInfoService userInfoService;

    public UserClientImpl(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public UserInfo getById(Long id) {
        return userInfoService.getById(id);
    }

    @Override
    public List<UserInfo> getByIds(List<Long> ids) {
        log.info("get users by ids={}", ids);
        return userInfoService.getByIds(ids);
    }

    @Override
    public void incrOrDecrColumn(IncrOrDecrColumnDto dto) {
        userInfoService.updateByIncrOrDecrColumnDto(dto);
    }
}
