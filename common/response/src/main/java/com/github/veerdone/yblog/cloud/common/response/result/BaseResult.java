package com.github.veerdone.yblog.cloud.common.response.result;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BaseResult {
    private int code;

    private String message;

    private String errCode;

    private String requestPath;

    public BaseResult() {
    }

    public BaseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public BaseResult(int code, String message, String errCode) {
        this.code = code;
        this.message = message;
        this.errCode = errCode;
    }

    public BaseResult(int code, String message, String errCode, String requestPath) {
        this.code = code;
        this.message = message;
        this.errCode = errCode;
        this.requestPath = requestPath;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(5);
        map.put("code", this.code);
        map.put("err_code", this.code);
        map.put("msg", this.message );
        map.put("request_path", this.requestPath);
        return map;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseResult that = (BaseResult) o;
        return code == that.code && Objects.equals(message, that.message) && Objects.equals(errCode, that.errCode) && Objects.equals(requestPath, that.requestPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message, errCode, requestPath);
    }

    @Override
    public String toString() {
        return "BaseResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", errCode='" + errCode + '\'' +
                ", requestPath='" + requestPath + '\'' +
                '}';
    }
}
