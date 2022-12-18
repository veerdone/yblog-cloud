package com.github.veerdone.yblog.cloud.common.exception;

public enum BizExceptionEnum {

    ;

    private final String message;

    private final int code;

    private final String errCode;

    BizExceptionEnum(String message, int code, String errCode) {
        this.message = message;
        this.code = code;
        this.errCode = errCode;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public String getErrCode() {
        return errCode;
    }
}
