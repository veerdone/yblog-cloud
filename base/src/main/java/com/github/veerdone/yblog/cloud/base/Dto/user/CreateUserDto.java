package com.github.veerdone.yblog.cloud.base.Dto.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CreateUserDto {
    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    @Length(max = 50, message = "邮箱最多为50个字符")
    private String email;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 8, max = 20, message = "密码最短为8个字符, 最长为20个字符")
    private String pass;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
