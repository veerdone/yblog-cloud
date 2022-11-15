package com.github.veerdone.third.party.oss.service.impl;

import com.github.veerdone.third.party.oss.service.ImageUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    @Override
    public String upload(MultipartFile multipartFile) {
        return null;
    }
}
