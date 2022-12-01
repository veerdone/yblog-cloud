package com.github.veerdone.third.party.email.service;

public interface EmailServer {
    void sendCaptcha(String email, String captcha);
}
