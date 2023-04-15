package com.github.veerdone.yblog.cloud.common.web;

import com.github.veerdone.yblog.cloud.common.exception.BizException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import com.github.veerdone.yblog.cloud.common.web.result.BaseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice
public class ExceptionController {
    public static final Logger log = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    public BaseResult methodNotFound(HttpServletRequest request) {
        ServiceExceptionEnum notFound = ServiceExceptionEnum.NOT_FOUND;

        return new BaseResult(notFound.getCode(), notFound.getMsg(), notFound.getErrCode(), request.getServletPath());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public BaseResult methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        String message = getMessage(e);
        ServiceExceptionEnum paramMistake = ServiceExceptionEnum.PARAM_MISTAKE;

        return new BaseResult(paramMistake.getCode(), message, paramMistake.getErrCode());
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
        log.warn("", e);
        ServiceExceptionEnum error = ServiceExceptionEnum.INNER_SERVICE_ERROR;

        return new BaseResult(error.getCode(), error.getMsg(), error.getErrCode());
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
            fieldErrors.forEach(fieldError -> builder.append(fieldError.getDefaultMessage()).append("!"));
        }

        return builder.toString();
    }
}
