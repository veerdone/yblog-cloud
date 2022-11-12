package com.github.veerdone.yblog.cloud.common.response;

import com.github.pagehelper.PageInfo;
import com.github.veerdone.yblog.cloud.common.response.Result.BaseResult;
import com.github.veerdone.yblog.cloud.common.response.Result.ListResult;
import com.github.veerdone.yblog.cloud.common.response.Result.ObjectResult;
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
        return null == returnType.getMethodAnnotation(ExceptionHandler.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof BaseResult) {
            return body;
        } else if (body instanceof List) {
            List list = (List) body;
            PageInfo pageInfo = new PageInfo(list);
            long total = pageInfo.getTotal();
            return ListResult.result(list, total);
        } else {
            return ObjectResult.result(body);
        }
    }
}