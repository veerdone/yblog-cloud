package com.github.veerdone.yblog.cloud.gateway.filter;

import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenFilter {
    @Bean
    public SaReactorFilter saReactorFilter() {
        return new SaReactorFilter()
                .addInclude("/**")
                .addExclude("/user/info/*", "/article/classify", "/article/label", "/user/login/*", "/article/*")
                .setAuth(a -> StpUtil.checkLogin());
    }
}
