package com.github.veerdone.yblog.cloud.base.exception;

public class ResultException extends RuntimeException{
    private String errCode;

    private int code;

    public ResultException(String message, String errCode, int code) {
        super(message);
        this.errCode = errCode;
        this.code = code;
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
