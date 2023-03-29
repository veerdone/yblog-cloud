package com.github.veerdone.yblog.cloud.common.exception;

public enum ServiceExceptionEnum {
    EMAIL_EXIST(10001, "邮箱已存在", "EMAIL_EXIST"),
    CAPTCHA_MISTAKE(10102, "验证码错误", "CAPTCHA_MISTAKE"),
    NOT_LOGIN(100103, "未登录", "NOT_LOGIN"),
    PARAM_MISTAKE(50400, "参数错误", "PARAM_MISTAKE"),
    NOT_FOUND(404, "请求路径不存在", "NOT_FOUND"),
    UNKNOWN_EXCEPTION(500, "系统繁忙,请稍后再试", "SYSTEM_BUSY")
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
