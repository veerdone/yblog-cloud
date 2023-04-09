package com.github.veerdone.yblog.cloud.gateway.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Set;

public class JwtFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Value("${config.jwt.key}")
    private byte[] key;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest oldRequest = exchange.getRequest();
        String requestPath = oldRequest.getPath().toString();
        if (requestPath.endsWith("/_common")) {
            return chain.filter(exchange);
        }

        List<String> authorization = oldRequest.getHeaders().get("Authorization");
        if (CollectionUtil.isEmpty(authorization)) {
            throw new ServiceException(ServiceExceptionEnum.NOT_LOGIN);
        }

        String token = authorization.get(0);
        JWT jwt = JWT.of(token).setKey(key);
        if (!jwt.validate(0)) {
            throw new ServiceException(ServiceExceptionEnum.NOT_LOGIN);
        }

        String query = getQuery(oldRequest.getQueryParams(),
                jwt.getPayload("uid").toString(), jwt.getPayload("role").toString());

        URI uri = oldRequest.getURI();
        URI newURI = UriComponentsBuilder.fromUri(uri).replaceQuery(query).build().encode().toUri();
        ServerHttpRequest request = oldRequest.mutate().uri(newURI).build();

        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return 1;
    }

    private String getQuery(MultiValueMap<String, String> queryParams, String userId, String role) {
        queryParams.set("user_id", userId);
        queryParams.set("role", role);

        Set<String> keySet = queryParams.keySet();
        StringBuilder builder = new StringBuilder(40);
        keySet.forEach(key -> {
            List<String> value = queryParams.get(key);
            builder.append(key);
            builder.append("=");
            if (CollectionUtil.isNotEmpty(value)) {
                builder.append(value.get(0));
                for (int i = 1; i < value.size(); i++) {
                    builder.append("&");
                    builder.append(key);
                    builder.append("=");
                    builder.append(value.get(i));
                }
            }
            builder.append("&");
        });

        return builder.substring(0, builder.length() - 1);
    }
}
