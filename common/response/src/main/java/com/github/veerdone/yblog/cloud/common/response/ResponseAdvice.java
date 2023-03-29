package com.github.veerdone.yblog.cloud.common.response;

import com.github.pagehelper.PageInfo;
import com.github.veerdone.yblog.cloud.common.response.result.BaseResult;
import com.github.veerdone.yblog.cloud.common.response.result.ListResult;
import com.github.veerdone.yblog.cloud.common.response.result.ObjectResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !("error".equals(returnType.getMethod().getName())) && null == returnType.getMethodAnnotation(ExceptionHandler.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof BaseResult) {
            return body;
        } else if (body instanceof List<?>) {
            List<?> list = (List<?>) body;
            PageInfo<?> pageInfo = PageInfo.of(list);
            long total = pageInfo.getTotal();
            boolean hasNextPage = pageInfo.isHasNextPage();

            return ListResult.result(list, total, hasNextPage);
        } else {
            return ObjectResult.result(body);
        }
    }
}
