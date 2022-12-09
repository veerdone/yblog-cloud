package com.github.veerdone.yblog.cloud.user.api;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@DubboService
public class UserClientImpl implements UserClient {
    @Resource
    private UserInfoService userInfoService;

    @Override
    public UserInfo getUserInfoById(Long id) {
        return userInfoService.getUserInfoById(id);
    }

    @Override
    public List<UserInfo> getUserInfoByIds(List<Long> ids) {
        return userInfoService.getUserInfoByIds(ids);
    }

    @Override
    public void incrOrDecrColumn(IncrOrDecrColumnDto dto) {
        LambdaUpdateWrapper<UserInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserInfo::getId, dto.getItemId())
                // eg: count=count+1 or count=count+-1
                .setSql(StrUtil.format("{}={}+{}", dto.getColumn(), dto.getColumn(), dto.getItemId()));
        userInfoService.updateByWrapper(wrapper);
    }
}
