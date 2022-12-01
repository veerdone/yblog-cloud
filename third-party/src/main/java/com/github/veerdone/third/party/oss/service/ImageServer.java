package com.github.veerdone.third.party.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageServer {
    String upload(MultipartFile multipartFile);
}
