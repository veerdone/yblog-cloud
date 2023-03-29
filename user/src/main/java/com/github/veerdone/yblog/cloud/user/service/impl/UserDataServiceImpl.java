package com.github.veerdone.yblog.cloud.user.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.user.CreateUserDto;
import com.github.veerdone.yblog.cloud.base.Vo.UserInfoVo;
import com.github.veerdone.yblog.cloud.base.convert.UserConvert;
import com.github.veerdone.yblog.cloud.base.model.UserData;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.common.util.PassEncoderUtil;
import com.github.veerdone.yblog.cloud.user.factory.user.UserRegisterFactory;
import com.github.veerdone.yblog.cloud.user.mapper.UserDataMapper;
import com.github.veerdone.yblog.cloud.user.service.UserDataService;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;


@Service
public class UserDataServiceImpl implements UserDataService {
    @Value("${config.jwt.key}")
    private byte[] key;

    @Value("${config.jwt.expireHour}")
    private Integer expireHour;

    @Resource
    private UserDataMapper userDataMapper;

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public UserInfoVo register(CreateUserDto dto) {
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
        String account = String.valueOf(System.currentTimeMillis()).substring(0, 6) + RandomUtil.randomNumbers(5);
        userData.setAccount(Long.valueOf(account));

        UserRegisterFactory.beforeHandler(userData);
        userDataMapper.insert(userData);

        UserInfo result = userInfoService.register(userData);
        redisTemplate.delete(key);

        UserInfoVo userInfoVo = UserConvert.INSTANCE.toUserInfoVo(result);
        DateTime date = DateUtil.date();
        String token = JWT.create()
                .setKey(this.key)
                .setPayload("uid", userData.getId())
                .setPayload("role", userData.getRole())
                .setIssuedAt(date)
                .setNotBefore(date)
                .setExpiresAt(date.offsetNew(DateField.HOUR, expireHour))
                .sign();
        userInfoVo.setToken(token);

        return userInfoVo;
    }
}
