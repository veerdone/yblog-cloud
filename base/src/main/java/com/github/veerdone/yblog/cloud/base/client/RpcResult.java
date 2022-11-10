package com.github.veerdone.yblog.cloud.base.client;

import com.github.veerdone.yblog.cloud.base.exception.BizException;

import java.io.Serializable;

public class RpcResult<T> implements Serializable {
    public static final Long serialVersionUID = 434124234352342L;

    private T data;

    private BizException bizException;

    public static <T> RpcResult<T> ok(T t) {
        RpcResult<T> rpcResult = new RpcResult<>();
        rpcResult.setData(t);
        return rpcResult;
    }

    public static <T> RpcResult<T> error(BizException bizException) {
        RpcResult<T> rpcResult = new RpcResult<>();
        rpcResult.setBizException(bizException);
        return rpcResult;
    }

    public boolean isOk() {
        return this.bizException == null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BizException getBizException() {
        return bizException;
    }

    public void setBizException(BizException bizException) {
        this.bizException = bizException;
    }

    @Override
    public String toString() {
        return "RpcResult{" +
                "data=" + data +
                ", bizException=" + bizException +
                '}';
    }
}
