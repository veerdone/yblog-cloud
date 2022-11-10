package com.github.veerdone.yblog.cloud.base.Dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CreateUserDto {
    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    @Max(value = 50, message = "邮箱最多为50个字符")
    private String email;

    @NotEmpty(message = "密码不能为空")
    @Min(value = 8, message = "密码最短为8个字符")
    @Max(value = 20, message = "密码最长为20个字符")
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
