package com.github.veerdone.yblog.cloud.base.exception;

public enum ServiceExceptionEnum {
    EMAIL_EXIST(10001, "邮箱已存在", "EMAIL_EXIST"),
    UNKNOWN_EXCEPTION(10500, "系统繁忙,请稍后再试", "SYSTEM_BUSY")
    ;
    private final int code;

    private final String msg;

    private final String errCode;

    ServiceExceptionEnum(int code, String msg, String errCode) {
        this.code = code;
        this.msg = msg;
        this.errCode = errCode;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getErrCode() {
        return errCode;
    }
}
