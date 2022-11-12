package com.github.veerdone.third.party.email.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailServiceFactory implements ApplicationContextAware {
    private static Map<String, EmailService> emailServiceMap;

    @Value("${config.email.default.service}")
    private String defaultEmailService;

    public static void setEmailServiceMap(Map<String, EmailService> map) {
        emailServiceMap = map;
    }

    public EmailService getDefaultEmailService() {
        return this.getEmailService(defaultEmailService);
    }

    public EmailService getEmailService(String serviceName) {
        return emailServiceMap.get(serviceName);
    }

    public static void addEmailService(String serviceName, EmailService emailService) {
        emailServiceMap.put(serviceName, emailService);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        emailServiceMap = applicationContext.getBeansOfType(EmailService.class);
    }
}
