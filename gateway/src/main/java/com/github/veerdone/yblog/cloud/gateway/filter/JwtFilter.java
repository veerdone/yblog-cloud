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
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

@Component
public class JwtFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Value("${config.jwt.key}")
    private byte[] key;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String requestPath = exchange.getRequest().getPath().toString();
        if (requestPath.endsWith("/_common")) {
            return chain.filter(exchange);
        }

        List<String> authorization = exchange.getRequest().getHeaders().get("Authorization");
        if (CollectionUtil.isEmpty(authorization)) {
            throw new ServiceException(ServiceExceptionEnum.NOT_LOGIN);
        }
        String token = authorization.get(0);
        JWT jwt = JWT.of(token).setKey(key);
        if (!jwt.validate(0)) {
            throw new ServiceException(ServiceExceptionEnum.NOT_LOGIN);
        }



        StringBuilder queryBuilder = new StringBuilder();
        URI uri = exchange.getRequest().getURI();
        String query = uri.getQuery();
        if (StrUtil.isNotEmpty(query)) {
            queryBuilder.append(query);
            if (query.charAt(query.length()-1) != '&') {
                queryBuilder.append('&');
            }
        }

        queryBuilder.append("userId=")
                .append(jwt.getPayload("uid"))
                .append("&role=")
                .append(jwt.getPayload("role"));
        URI newURI = UriComponentsBuilder.fromUri(uri).replaceQuery(queryBuilder.toString()).build().encode().toUri();
        ServerHttpRequest request = exchange.getRequest().mutate().uri(newURI).build();

        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
