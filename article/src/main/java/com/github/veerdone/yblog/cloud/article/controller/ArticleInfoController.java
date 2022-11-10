package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/article")
public class ArticleInfoController {
    @Resource
    private ArticleInfoService articleInfoService;

    @GetMapping("/test")
    public void test() {
        articleInfoService.test();
    }

    @GetMapping("/test1")
    public void test1() {
        throw new RuntimeException();
    }
}
