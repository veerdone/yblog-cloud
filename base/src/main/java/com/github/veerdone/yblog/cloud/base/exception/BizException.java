package com.github.veerdone.yblog.cloud.base.exception;

import java.util.Objects;

public class BizException extends RuntimeException {
    private int code;

    private String errCode;

    public BizException() {
    }

    public BizException(String message, int code, String errCode) {
        super(message);
        this.code = code;
        this.errCode = errCode;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BizException that = (BizException) o;
        return code == that.code && Objects.equals(errCode, that.errCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, errCode);
    }

    @Override
    public String toString() {
        return "BizException{" +
                "code=" + code +
                ", errCode='" + errCode + '\'' +
                '}';
    }
}
