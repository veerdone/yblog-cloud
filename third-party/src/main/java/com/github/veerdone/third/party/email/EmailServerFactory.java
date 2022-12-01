package com.github.veerdone.third.party.email;

import com.github.veerdone.third.party.email.service.EmailServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmailServerFactory implements ApplicationContextAware {
    private static Map<String, EmailServer> emailServiceMap;

    @Value("${config.email.default.service}")
    private String defaultEmailService;

    public static void setEmailServiceMap(Map<String, EmailServer> map) {
        emailServiceMap = map;
    }

    public EmailServer getDefaultEmailService() {
        return this.getEmailService(defaultEmailService);
    }

    public EmailServer getEmailService(String serviceName) {
        return emailServiceMap.get(serviceName);
    }

    public static void addEmailService(String serviceName, EmailServer emailServer) {
        emailServiceMap.put(serviceName, emailServer);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        emailServiceMap = applicationContext.getBeansOfType(EmailServer.class);
    }
}
