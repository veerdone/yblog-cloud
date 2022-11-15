package com.github.veerdone.third.party.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {
    String upload(MultipartFile multipartFile);

}
