package com.github.veerdone.yblog.cloud.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.user.CreateUserDto;
import com.github.veerdone.yblog.cloud.base.model.UserData;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.common.util.PassEncoderUtil;
import com.github.veerdone.yblog.cloud.user.mapper.UserDataMapper;
import com.github.veerdone.yblog.cloud.user.service.UserDataService;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;


@Service
public class UserDataServiceImpl implements UserDataService {
    @Resource
    private UserDataMapper userDataMapper;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public UserInfo create(CreateUserDto dto) {
        UserData userData = new UserData();
        userData.setEmail(dto.getEmail());
        LambdaQueryWrapper<UserData> wrapper = new LambdaQueryWrapper<>(userData);
        if (Objects.nonNull(userDataMapper.selectOne(wrapper))) {
            throw new ServiceException(ServiceExceptionEnum.EMAIL_EXIST);
        }

        String key = CacheKey.USER_REGISTER_CAPTCHA + dto.getEmail();
        Object cacheCaptcha = redisTemplate.opsForValue().get(key);
        if (Objects.isNull(cacheCaptcha) || !Objects.equals(cacheCaptcha, dto.getCaptcha())) {
            throw new ServiceException(ServiceExceptionEnum.CAPTCHA_MISTAKE);
        }

        userData.setPass(PassEncoderUtil.encode(dto.getPass()));
        String account = String.valueOf(System.currentTimeMillis()).substring(0, 6) + RandomUtil.randomString(5);
        userData.setAccount(Long.valueOf(account));
        userDataMapper.insert(userData);

        UserInfo userInfo = new UserInfo();
        userInfo.setId(userData.getId());
        userInfo.setCreateTime(userData.getCreateTime());
        userInfo.setUpdateTime(userData.getUpdateTime());
        userInfo.setUserName("用户" + RandomUtil.randomString(6));

        UserInfo result = userInfoService.create(userInfo);
        redisTemplate.delete(key);

        return result;
    }
}
