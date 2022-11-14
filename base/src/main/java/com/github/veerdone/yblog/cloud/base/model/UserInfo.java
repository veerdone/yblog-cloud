package com.github.veerdone.yblog.cloud.base.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName("yblog_user_info")
public class UserInfo implements Serializable {
    public static final Long serialVersionUID = 5353454545345453L;

    private Long id;

    private String userName;

    private Integer sex;

    private String selfDescription;

    private Integer fan;

    private Integer collection;

    private Integer likes;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSelfDescription() {
        return selfDescription;
    }

    public void setSelfDescription(String selfDescription) {
        this.selfDescription = selfDescription;
    }

    public Integer getFan() {
        return fan;
    }

    public void setFan(Integer fan) {
        this.fan = fan;
    }

    public Integer getCollection() {
        return collection;
    }

    public void setCollection(Integer collection) {
        this.collection = collection;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
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
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(id, userInfo.id) && Objects.equals(userName, userInfo.userName) && Objects.equals(sex, userInfo.sex) && Objects.equals(selfDescription, userInfo.selfDescription) && Objects.equals(fan, userInfo.fan) && Objects.equals(collection, userInfo.collection) && Objects.equals(likes, userInfo.likes) && Objects.equals(createTime, userInfo.createTime) && Objects.equals(updateTime, userInfo.updateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, sex, selfDescription, fan, collection, likes, createTime, updateTime);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", sex=" + sex +
                ", selfDescription='" + selfDescription + '\'' +
                ", fan=" + fan +
                ", collection=" + collection +
                ", likes=" + likes +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
