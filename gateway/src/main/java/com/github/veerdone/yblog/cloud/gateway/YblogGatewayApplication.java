package com.github.veerdone.yblog.cloud.gateway;

import com.github.veerdone.yblog.cloud.common.response.ResponseAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = ResponseAutoConfiguration.class)
public class YblogGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(YblogGatewayApplication.class);
    }
}
