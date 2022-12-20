package com.github.veerdone.yblog.cloud.gateway;

import com.github.veerdone.yblog.cloud.common.exception.BizException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {
    public JsonExceptionHandler(ErrorAttributes errorAttributes,
                                WebProperties.Resources resources,
                                ErrorProperties errorProperties,
                                ApplicationContext applicationContext) {
        super(errorAttributes, resources, errorProperties, applicationContext);
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        Object status = errorAttributes.get("status");
        if (Objects.isNull(status)) {
            return 200;
        }
        return (int) status;
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        Throwable error = super.getError(request);
        if (error instanceof BizException) {
            BizException bizEx = (BizException) error;

            return buildResponse(bizEx.getCode(), bizEx.getErrCode(), bizEx.getMessage());
        } else if (error instanceof ServiceException) {
            ServiceException serviceEx = (ServiceException) error;

            return buildResponse(serviceEx.getCode(), serviceEx.getErrCode(), serviceEx.getMessage());
        } else if (error instanceof ResponseStatusException) {
            ResponseStatusException ex = (ResponseStatusException) error;

            return buildResponse(ex.getStatus().value(), String.valueOf(ex.getRawStatusCode()), ex.getMessage());
        }
        ServiceExceptionEnum unknownException = ServiceExceptionEnum.UNKNOWN_EXCEPTION;
        return buildResponse(unknownException.getCode(), unknownException.getErrCode(), unknownException.getMsg());
    }

    private Map<String, Object> buildResponse(int code, String errCode, String msg) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("code", code);
        map.put("err_code",errCode);
        map.put("msg", msg);
        return map;
    }
}
