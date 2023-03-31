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
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/register_captcha/_common")
    public void registerCaptcha(@RequestParam("email") String email) {
        AsyncExecutor.execute(() -> emailService.userRegisterCaptcha(email));
    }

    @GetMapping("/login_captcha/_common")
    public void loginCaptcha(@RequestParam("email") String email) {
        AsyncExecutor.execute(() -> emailService.userLoginCaptcha(email));
    }
}
