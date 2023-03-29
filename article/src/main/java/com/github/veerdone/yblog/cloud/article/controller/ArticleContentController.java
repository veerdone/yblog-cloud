package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.article.service.ArticleContentService;
import com.github.veerdone.yblog.cloud.base.Dto.post.CreateArticleDto;
import com.github.veerdone.yblog.cloud.base.Dto.post.UpdateArticleDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/article")
public class ArticleContentController {
    @Resource
    private ArticleContentService articleContentService;

    @PostMapping
    public void createPost(@RequestBody @Validated CreateArticleDto dto) {
        articleContentService.create(dto);
    }

    @PutMapping
    public void updateById(@RequestBody @Validated UpdateArticleDto dto) {
        articleContentService.updateById(dto);
    }

    @GetMapping("/detail/{articleId}")
    public ArticleDetailVo detail(@PathVariable Long articleId) {
        return articleContentService.getArticleDetailVoById(articleId);
    }
}
