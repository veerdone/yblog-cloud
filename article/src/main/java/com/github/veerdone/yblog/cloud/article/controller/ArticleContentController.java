package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.article.service.ArticleContentService;
import com.github.veerdone.yblog.cloud.base.Dto.post.CreateArticleDto;
import com.github.veerdone.yblog.cloud.base.Dto.post.UpdateArticleDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/article")
public class ArticleContentController {
    @Resource
    private ArticleContentService articleContentService;

    @PostMapping("/create")
    public void createPost(@RequestBody @Validated CreateArticleDto dto) {
        articleContentService.create(dto);
    }

    @PutMapping("/update")
    public void updateById(@RequestBody @Validated UpdateArticleDto dto) {
        articleContentService.updateById(dto);
    }
}
