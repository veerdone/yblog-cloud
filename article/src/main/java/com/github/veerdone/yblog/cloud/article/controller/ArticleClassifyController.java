package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.article.service.ArticleClassifyService;
import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article/classify")
public class ArticleClassifyController {
    private final ArticleClassifyService articleClassifyService;

    public ArticleClassifyController(ArticleClassifyService articleClassifyService) {
        this.articleClassifyService = articleClassifyService;
    }

    @PostMapping
    public void create(@RequestBody @Validated ArticleClassify classify) {
        articleClassifyService.create(classify);
    }

    @GetMapping
    public List<ArticleClassify> list() {
        return articleClassifyService.list();
    }
}
