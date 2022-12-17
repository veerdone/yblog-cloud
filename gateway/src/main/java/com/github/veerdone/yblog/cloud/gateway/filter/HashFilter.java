package com.github.veerdone.yblog.cloud.gateway.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.veerdone.yblog.cloud.common.response.result.BaseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class HashFilter implements GlobalFilter, Ordered {
    @Value("${config.filter.hash-key:key}")
    private String key;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        String hash = queryParams.getFirst("hash");
        String timestamp = queryParams.getFirst("timestamp");
        if (StrUtil.isBlank(hash) || StrUtil.isBlank(timestamp)) {
            return fail(exchange.getResponse());
        }

        long queryTimestamp = Long.parseLong(timestamp);
        long currentTimeMillis = System.currentTimeMillis();
        String s = SecureUtil.md5(currentTimeMillis + key);
        long poor = currentTimeMillis - queryTimestamp;
        if ((-500 <= poor && poor <= 500) && Objects.equals(s, hash)) {
            return chain.filter(exchange);
        }

        return fail(exchange.getResponse());
    }

    private Mono<Void> fail(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add("Content-Type", "application/json;charset=utf8");
        BaseResult result = new BaseResult(50400, "验证失败", "VALIDATE_FAIL");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(result);
        } catch (JsonProcessingException e) {
            bytes = new byte[]{};
        }
        DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(dataBuffer));
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
