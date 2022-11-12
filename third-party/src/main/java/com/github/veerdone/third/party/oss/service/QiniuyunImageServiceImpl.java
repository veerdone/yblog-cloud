package com.github.veerdone.third.party.oss.service;

import com.github.veerdone.yblog.cloud.base.exception.ServiceException;
import com.github.veerdone.yblog.cloud.base.exception.ServiceExceptionEnum;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class QiniuyunImageServiceImpl implements ImageService {
    private static final Logger log = LoggerFactory.getLogger(QiniuyunImageServiceImpl.class);
    
    @Value("${config.qiniuyun.accessKey}")
    private String accessKey;

    @Value("${config.qiniuyun.secretKey}")
    private String secretKey;

    @Value("${config.qiniuyun.bucket}")
    private String bucket;

    @Value("${config.qiniuyun.domain}")
    private String domain;


    @Override
    public String upload(MultipartFile multipartFile) {
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(accessKey, secretKey);
        String uploadToken = auth.uploadToken(bucket);

        DefaultPutRet putRet = null;
        try {
            Response response = uploadManager.put(multipartFile.getBytes(), null, uploadToken);
            putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException e) {
            try {
                log.warn("图片上传失败: ");
                log.warn("{}", e.response.bodyString());
                log.warn("",e);
            } catch (QiniuException ex) {
                log.warn("", ex);
                throw new ServiceException(ServiceExceptionEnum.UNKNOWN_EXCEPTION);
            }
        } catch (IOException e) {
            log.warn("图片上传失败: ", e);
            throw new ServiceException(ServiceExceptionEnum.UNKNOWN_EXCEPTION);
        }
        return domain + putRet.hash;
    }
}
