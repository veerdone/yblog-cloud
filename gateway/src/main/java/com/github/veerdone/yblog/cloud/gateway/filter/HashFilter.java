package com.github.veerdone.yblog.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class HashFilter implements GlobalFilter, Ordered {
    @Value("${config.filter.hash.key:key}")
    private String key;

    @Value("${config.filter.hash.enable:true}")
    private boolean enable;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (enable) {
            ServerHttpRequest request = exchange.getRequest();
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            String requestHash = queryParams.getFirst("hash");
            String timestamp = queryParams.getFirst("timestamp");
            if (StrUtil.isBlank(requestHash) || StrUtil.isBlank(timestamp)) {
                throw new ServiceException(ServiceExceptionEnum.CAPTCHA_MISTAKE);
            }

            long queryTimestamp;
            try {
                queryTimestamp = Long.parseLong(timestamp);
            } catch (NumberFormatException e) {
                throw new ServiceException(ServiceExceptionEnum.CAPTCHA_MISTAKE);
            }

            long currentTimeMillis = System.currentTimeMillis();
            String hash = SecureUtil.md5(currentTimeMillis + key);
            long poor = currentTimeMillis - queryTimestamp;
            if ((-500 <= poor && poor <= 500) && Objects.equals(hash, requestHash)) {
                return chain.filter(exchange);
            }
            throw new ServiceException(ServiceExceptionEnum.CAPTCHA_MISTAKE);
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
