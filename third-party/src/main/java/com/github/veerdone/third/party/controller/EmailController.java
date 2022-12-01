package com.github.veerdone.third.party.controller;

import com.github.veerdone.third.party.email.service.EmailService;
import com.github.veerdone.yblog.cloud.common.util.async.AsyncExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/email")
public class EmailController {
    @Resource
    private EmailService emailService;

    @GetMapping("/register_captcha")
    public void register_captcha(@RequestParam("email") String email) {
        AsyncExecutor.execute(() -> {
            emailService.userRegisterCaptcha(email);
        });
    }
}
