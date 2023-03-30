package com.github.veerdone.third.party.email.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.github.veerdone.third.party.email.EmailServerFactory;
import com.github.veerdone.third.party.email.service.EmailService;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class EmailServiceImpl implements EmailService {
    @Resource
    private EmailServerFactory emailServerFactory;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void userRegisterCaptcha(String email) {
        String captcha = RandomUtil.randomString(6);
        emailServerFactory.getDefaultEmailService().sendCaptcha(email, captcha);
        redisTemplate.opsForValue().set(CacheKey.USER_REGISTER_CAPTCHA + email, captcha, 5, TimeUnit.MINUTES);
    }

    @Override
    public void userLoginCaptcha(String email) {
        String captcha = RandomUtil.randomString(6);
        emailServerFactory.getDefaultEmailService().sendCaptcha(email, captcha);
        redisTemplate.opsForValue().set(CacheKey.USER_LOGIN_CAPTCHA + email, captcha, 5, TimeUnit.MINUTES);
    }


}
