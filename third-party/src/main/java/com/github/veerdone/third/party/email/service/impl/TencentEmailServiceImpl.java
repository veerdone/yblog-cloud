package com.github.veerdone.third.party.email.service.impl;

import com.github.veerdone.third.party.email.service.EmailService;
import com.google.gson.JsonObject;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.ses.v20201002.SesClient;
import com.tencentcloudapi.ses.v20201002.models.SendEmailRequest;
import com.tencentcloudapi.ses.v20201002.models.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TencentEmailServiceImpl implements EmailService, InitializingBean {
    private static final Logger log = LoggerFactory.getLogger(TencentEmailServiceImpl.class);

    private final TencentEmailConfig tencentEmailConfig;

    private SesClient sesClient;

    @Value("${config.email.captcha.templateId}")
    private Long templateId;

    public TencentEmailServiceImpl(TencentEmailConfig tencentEmailConfig) {
        this.tencentEmailConfig = tencentEmailConfig;
    }

    @Override
    public void sendCaptcha(String email, String captcha) {
        SendEmailRequest request = new SendEmailRequest();
        request.setFromEmailAddress(tencentEmailConfig.getFromAddress());
        request.setDestination(new String[]{email});

        Template template = new Template();
        template.setTemplateID(templateId);

        JsonObject object = new JsonObject();
        object.addProperty("username", "用户");
        object.addProperty("code", captcha);

        template.setTemplateData(object.toString());
        request.setTemplate(template);
        request.setSubject("登录验证码");

        try {
            sesClient.SendEmail(request);
        } catch (TencentCloudSDKException e) {
            log.warn("发送邮件失败: ", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Credential cred = new Credential(tencentEmailConfig.getSecretId(), tencentEmailConfig.getSecretKey());
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint(tencentEmailConfig.getEndpoint());
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);

        sesClient = new SesClient(cred, "ap-hongkong", clientProfile);
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
}
