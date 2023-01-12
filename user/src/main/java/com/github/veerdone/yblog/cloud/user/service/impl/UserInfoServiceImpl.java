package com.github.veerdone.yblog.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.github.veerdone.yblog.cloud.base.Dto.user.UpdateUserInfoDto;
import com.github.veerdone.yblog.cloud.base.convert.UserConvert;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.user.factory.user.UserRegisterFactory;
import com.github.veerdone.yblog.cloud.user.mapper.UserInfoMapper;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public UserInfo getUserInfoById(Long id) {
        String key = CacheKey.USER_INFO_QUERY_BY_ID + id;
        Object cacheUserInfo = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(cacheUserInfo)) {
            return (UserInfo) cacheUserInfo;
        }
        UserInfo userInfo = userInfoMapper.selectById(id);
        if (Objects.nonNull(userInfo)) {
            redisTemplate.opsForValue().set(key, userInfo, 30, TimeUnit.MINUTES);
        }

        return userInfo;
    }

    @Override
    public UserInfo create(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
        UserRegisterFactory.asyncAfterHandler(userInfo);

        return this.getUserInfoById(userInfo.getId());
    }

    @Override
    public List<UserInfo> getUserInfoByIds(List<Long> ids) {
        List<UserInfo> userInfoList = new ArrayList<>(ids.size());
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i).equals(0L)) {
                userInfoList.add(null);
                continue;
            }
            int prei = i - 1;
            if (prei > 0 && ids.get(prei).equals(ids.get(i))) {
                userInfoList.add(userInfoList.get(prei));
                continue;
            }

            UserInfo userInfo = this.getUserInfoById(ids.get(i));
            userInfoList.add(userInfo);
        }

        return userInfoList;
    }

    @Override
    public void updateUserInfoById(UpdateUserInfoDto dto) {
        redisTemplate.delete(CacheKey.USER_INFO_QUERY_BY_ID + dto.getId());
        UserInfo userInfo = UserConvert.INSTANCE.toUserInfo(dto);
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public void updateByWrapper(Wrapper<UserInfo> wrapper) {
        userInfoMapper.update(null, wrapper);
    }
}
