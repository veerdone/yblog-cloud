package com.github.veerdone.yblog.cloud.base.Dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginUserDto {
    @Email(message = "邮箱格式错误")
    @NotEmpty(message = "邮箱不能为空")
    private String email;

    @Size(max = 6, message = "验证码为6个字符")
    private String captcha;

    @Size(max = 20, message = "密码最长为20个字符")
    private String pass;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
