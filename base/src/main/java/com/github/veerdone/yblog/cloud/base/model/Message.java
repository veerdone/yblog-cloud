package com.github.veerdone.yblog.cloud.base.model;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName("yblog_message")
public class Message implements Serializable {
    private static final long serialVersionUID = 4434234345452L;

    private Long id;

    private Long receiverId;

    private String msg;

    private Integer msgType;

    private Integer status;

    private Long createTime;

    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(receiverId, message.receiverId) && Objects.equals(msg, message.msg) && Objects.equals(msgType, message.msgType) && Objects.equals(status, message.status) && Objects.equals(createTime, message.createTime) && Objects.equals(updateTime, message.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receiverId, msg, msgType, status, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", receiverId=" + receiverId +
                ", msg='" + msg + '\'' +
                ", msgType=" + msgType +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
