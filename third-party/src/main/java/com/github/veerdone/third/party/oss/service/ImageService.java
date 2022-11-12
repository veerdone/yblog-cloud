package com.github.veerdone.third.party.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String upload(MultipartFile multipartFile);
}
