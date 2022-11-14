package com.github.veerdone.third.party.email.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.github.veerdone.third.party.email.service.EmailServiceFactory;
import com.github.veerdone.third.party.email.service.SendEmailService;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class SendEmailServiceImpl implements SendEmailService {
    @Resource
    private EmailServiceFactory emailServiceFactory;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void userRegisterCaptcha(String email) {
        String captcha = RandomUtil.randomString(6);
        emailServiceFactory.getDefaultEmailService().sendCaptcha(email, captcha);
        redisTemplate.opsForValue().set(CacheKey.USER_REGISTER_CAPTCHA + email, captcha, 5, TimeUnit.MINUTES);
    }


}
