package com.github.veerdone.yblog.cloud.article.config;

import com.github.veerdone.yblog.cloud.base.api.user.UserClientGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClientBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@GrpcClientBean(
        clazz = UserClientGrpc.UserClientBlockingStub.class,
        beanName = "userClientBlockingStub",
        client = @GrpcClient("user-service")
)
public class GRpcClientConfiguration {
    @Bean("userClientBlockingStub")
    public UserClientGrpc.UserClientBlockingStub userClientBlockingStub(UserClientGrpc.UserClientBlockingStub userClientBlockingStub) {
        return userClientBlockingStub;
    }
}
