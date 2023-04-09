package com.github.veerdone.yblog.cloud.user.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.base.Dto.user.CreateUserDto;
import com.github.veerdone.yblog.cloud.base.Dto.user.LoginUserDto;
import com.github.veerdone.yblog.cloud.base.Vo.UserInfoVo;
import com.github.veerdone.yblog.cloud.base.convert.UserConvert;
import com.github.veerdone.yblog.cloud.base.model.UserData;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import com.github.veerdone.yblog.cloud.common.util.PassEncoderUtil;
import com.github.veerdone.yblog.cloud.user.factory.user.UserRegisterFactory;
import com.github.veerdone.yblog.cloud.user.mapper.UserDataMapper;
import com.github.veerdone.yblog.cloud.user.service.UserDataService;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;


@Service
public class UserDataServiceImpl implements UserDataService {
    private static final Logger log = LoggerFactory.getLogger(UserDataServiceImpl.class);

    private final UserDataMapper userDataMapper;

    private final UserInfoService userInfoService;

    private final RedisTemplate<String, Object> redisTemplate;

    public UserDataServiceImpl(UserDataMapper userDataMapper, UserInfoService userInfoService,
                               RedisTemplate<String, Object> redisTemplate) {
        this.userDataMapper = userDataMapper;
        this.userInfoService = userInfoService;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserData getByEmail(String email) {
        UserData userData = new UserData();
        userData.setEmail(email);
        LambdaQueryWrapper<UserData> wrapper = new LambdaQueryWrapper<>(userData);

        return userDataMapper.selectOne(wrapper);
    }

    @Override
    @Transactional
    public UserInfoVo register(CreateUserDto dto) {
        UserData userData = new UserData();
        userData.setEmail(dto.getEmail());
        if (Objects.nonNull(this.getByEmail(dto.getEmail()))) {
            throw new ServiceException(ServiceExceptionEnum.EMAIL_EXIST);
        }

        String key = CacheKey.USER_REGISTER_CAPTCHA + dto.getEmail();
        log.debug("get user_register_captcha by cache_key={}", key);
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

        log.info("new registered user, userId={}", userData.getId());
        return generateUserInfoVo(userData, result);
    }

    @Override
    public UserInfoVo loginByCaptcha(LoginUserDto dto) {
        LambdaQueryWrapper<UserData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserData::getEmail, dto.getEmail());
        UserData dbUserData = userDataMapper.selectOne(wrapper);
        String cacheKey = CacheKey.USER_LOGIN_CAPTCHA + dto.getEmail();
        log.debug("get user_login_captcha by cache_key={}", cacheKey);
        Object cacheCaptcha = redisTemplate.opsForValue().get(cacheKey);
        if (Objects.isNull(dbUserData) || (Objects.isNull(cacheCaptcha) || !Objects.equals(cacheCaptcha, dto.getCaptcha()))) {
            throw new ServiceException(ServiceExceptionEnum.EMAIL_OR_CAPTCHA_MISTAKE);
        }
        redisTemplate.delete(cacheKey);

        return generateUserInfoVo(dbUserData, null);
    }

    @Override
    public UserInfoVo loginByPass(LoginUserDto dto) {
        LambdaQueryWrapper<UserData> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserData::getEmail, dto.getEmail());
        UserData dbUserData = userDataMapper.selectOne(wrapper);
        if (Objects.isNull(dbUserData) || !PassEncoderUtil.matches(dto.getPass(), dbUserData.getPass())) {
            throw new ServiceException(ServiceExceptionEnum.EMAIL_OR_PASS_MISTAKE);
        }

        return generateUserInfoVo(dbUserData, null);
    }

    private UserInfoVo generateUserInfoVo(UserData userData, UserInfo userInfo) {
        if (Objects.isNull(userInfo)) {
            userInfo = userInfoService.getById(userData.getId());
        }
        UserInfoVo userInfoVo = UserConvert.INSTANCE.toUserInfoVo(userInfo);
        StpUtil.login(userData.getId());
        String tokenValue = StpUtil.getTokenValue();
        userInfoVo.setToken(tokenValue);

        return userInfoVo;
    }
}
