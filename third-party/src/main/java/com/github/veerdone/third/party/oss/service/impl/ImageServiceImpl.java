package com.github.veerdone.third.party.oss.service.impl;

import com.github.veerdone.third.party.oss.ImageServerFactory;
import com.github.veerdone.third.party.oss.service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Service
public class ImageServiceImpl implements ImageService {
    @Resource
    private ImageServerFactory imageServerFactory;


    @Override
    public String upload(MultipartFile multipartFile) {
        return imageServerFactory.getDefaultServer().upload(multipartFile);
    }
}
