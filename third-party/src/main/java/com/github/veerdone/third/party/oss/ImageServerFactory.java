package com.github.veerdone.third.party.oss;

import com.github.veerdone.third.party.oss.service.ImageServer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ImageServerFactory implements ApplicationContextAware {
    private static Map<String, ImageServer> serverMap;

    @Value("${config.upload.image.default.service}")
    private String defaultService;

    public ImageServer getDefaultServer() {
        return this.getServer(defaultService);
    }

    public ImageServer getServer(String serviceName) {
        return serverMap.get(serviceName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        serverMap = applicationContext.getBeansOfType(ImageServer.class);
    }
}
