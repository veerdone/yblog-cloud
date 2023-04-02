package com.github.veerdone.yblog.cloud.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.Dto.user.UpdateUserInfoDto;
import com.github.veerdone.yblog.cloud.base.convert.UserConvert;
import com.github.veerdone.yblog.cloud.base.model.UserData;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.user.factory.user.UserRegisterFactory;
import com.github.veerdone.yblog.cloud.user.mapper.UserInfoMapper;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final UserInfoMapper userInfoMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    public UserInfoServiceImpl(UserInfoMapper userInfoMapper, RedisTemplate<String, Object> redisTemplate) {
        this.userInfoMapper = userInfoMapper;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserInfo getById(Long id) {
        String key = CacheKey.USER_INFO_QUERY_BY_ID + id;
        log.debug("get user_info by cache_key={}", key);
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
    public UserInfo register(UserData userData) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userData.getId());
        userInfo.setCreateTime(userData.getCreateTime());
        userInfo.setUpdateTime(userData.getUpdateTime());
        userInfo.setUserName("用户" + RandomUtil.randomString(6));

        userInfoMapper.insert(userInfo);
        UserRegisterFactory.asyncAfterHandler(userInfo);

        return this.getById(userInfo.getId());
    }

    @Override
    public List<UserInfo> getByIds(List<Long> ids) {
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

            UserInfo userInfo = this.getById(ids.get(i));
            userInfoList.add(userInfo);
        }

        return userInfoList;
    }

    @Override
    public void updateById(UpdateUserInfoDto dto) {
        String cacheKey = CacheKey.USER_INFO_QUERY_BY_ID + dto.getId();
        log.debug("del user_info cache by cache_key={}", cacheKey);
        redisTemplate.delete(cacheKey);
        UserInfo userInfo = UserConvert.INSTANCE.toUserInfo(dto);
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public void updateByIncrOrDecrColumnDto(IncrOrDecrColumnDto dto) {
        String cacheKey = CacheKey.USER_INFO_QUERY_BY_ID + dto.getItemId();
        log.debug("del user_info cache by cache_key={}", cacheKey);
        redisTemplate.delete(cacheKey);
        LambdaUpdateWrapper<UserInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserInfo::getId, dto.getItemId())
                .setSql(StrUtil.format("{}={}+{}", dto.getColumn(), dto.getColumn(), dto.getItemId()));
        userInfoMapper.update(null, wrapper);
    }
}
