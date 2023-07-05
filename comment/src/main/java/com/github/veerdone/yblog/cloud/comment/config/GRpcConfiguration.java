package com.github.veerdone.yblog.cloud.comment.config;

import com.github.veerdone.yblog.cloud.base.api.article.ArticleClientGrpc;
import com.github.veerdone.yblog.cloud.base.api.user.UserClientGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.client.inject.GrpcClientBean;
import net.devh.boot.grpc.client.inject.GrpcClientBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@GrpcClientBeans({
        @GrpcClientBean(clazz = UserClientGrpc.UserClientBlockingStub.class,
                client = @GrpcClient("user-service"),
                beanName = "userClientBlockingStub"
        ),
        @GrpcClientBean(
                clazz = ArticleClientGrpc.ArticleClientBlockingStub.class,
                client = @GrpcClient("article-service"),
                beanName = "articleClientBlockingStub"
        )
})
public class GRpcConfiguration {
    @Bean
    public UserClientGrpc.UserClientBlockingStub userClientBlockingStub(
            UserClientGrpc.UserClientBlockingStub userClientBlockingStub
    ) {
        return userClientBlockingStub;
    }

    @Bean
    public ArticleClientGrpc.ArticleClientBlockingStub articleClientBlockingStub(
            ArticleClientGrpc.ArticleClientBlockingStub articleClientBlockingStub
    ) {
        return articleClientBlockingStub;
    }
}
