package com.github.veerdone.yblog.cloud.base.model;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Objects;

@TableName("yblog_user_data")
public class UserData implements Serializable {
    private static final long serialVersionUID = 53535353534343L;

    private Long id;

    private Long account;

    private String pass;

    private String email;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    private Integer role;

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        UserData userData = (UserData) o;
        return Objects.equals(id, userData.id) && Objects.equals(account, userData.account) && Objects.equals(pass, userData.pass) && Objects.equals(email, userData.email) && Objects.equals(createTime, userData.createTime) && Objects.equals(updateTime, userData.updateTime) && Objects.equals(role, userData.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, account, pass, email, createTime, updateTime, role);
    }

    @Override
    public String toString() {
        return "UserData{" +
                "id=" + id +
                ", account=" + account +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", role=" + role +
                '}';
    }
}
