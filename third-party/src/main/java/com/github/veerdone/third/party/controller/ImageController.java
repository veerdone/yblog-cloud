package com.github.veerdone.third.party.controller;

import com.github.veerdone.third.party.oss.service.ImageService;
import com.github.veerdone.yblog.cloud.common.response.result.ObjectResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Resource
    private ImageService imageService;

    @PostMapping("/upload")
    public ObjectResult<String> upload(@RequestParam("image") MultipartFile multipartFile) {
        String imgUrl = imageService.upload(multipartFile);
        return ObjectResult.result(imgUrl);
    }
}
