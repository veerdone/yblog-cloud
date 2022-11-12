package com.github.veerdone.yblog.cloud.base.exception;

public class ServiceException extends RuntimeException{
    private String errCode;

    private int code;

    public ServiceException(String message, String errCode, int code) {
        super(message);
        this.errCode = errCode;
        this.code = code;
    }

    public ServiceException(ServiceExceptionEnum exceptionEnum) {
        this(exceptionEnum.getMsg(), exceptionEnum.getErrCode(), exceptionEnum.getCode());
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
