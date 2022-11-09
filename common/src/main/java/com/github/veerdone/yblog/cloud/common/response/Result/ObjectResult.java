package com.github.veerdone.yblog.cloud.common.response.Result;

public class ObjectResult<T> extends BaseResult {
    private T data;

    public ObjectResult(int code, String message, String errCode, T data) {
        super(code, message, errCode);
        this.data = data;
    }

    public ObjectResult(int code, String message, T data) {
        super(code, message);
        this.data = data;
    }

    public static <T> ObjectResult<T> result(T t) {
        return new ObjectResult<>(0, "ok",  t);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
