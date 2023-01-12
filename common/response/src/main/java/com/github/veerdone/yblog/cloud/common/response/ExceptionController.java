package com.github.veerdone.yblog.cloud.common.response;

import com.github.veerdone.yblog.cloud.common.exception.BizException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.response.result.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionController {
    public static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResult methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        String message = getMessage(e);
        return new BaseResult(100401,  message, "PARAMS_MISTAKE");
    }

    @ExceptionHandler(BizException.class)
    public BaseResult bizExceptionHandler(BizException e) {
        return new BaseResult(e.getCode(), e.getMessage(), e.getErrCode());
    }

    @ExceptionHandler(ServiceException.class)
    public BaseResult serviceExceptionHandler(ServiceException e) {
        return new BaseResult(e.getCode(), e.getMessage(), e.getErrCode());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResult runtimeExceptionHandler(RuntimeException e) {
        log.warn("{}", e.getClass());
        log.warn("", e);
        return new BaseResult(10500, "系统繁忙, 请稍后再试");
    }

    private String getMessage(Exception e) {
        BindingResult result = null;
        if (e instanceof MethodArgumentNotValidException) {
            result = ((MethodArgumentNotValidException) e).getBindingResult();
        } else if (e instanceof BindException) {
            result = ((BindException) e).getBindingResult();
        }
        StringBuilder builder = new StringBuilder();
        if (result != null) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.forEach(fieldError -> {
                builder.append(fieldError.getDefaultMessage()).append("!");
            });
        }

        return builder.toString();
    }
}
