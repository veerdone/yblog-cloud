package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.article.service.ArticleContentService;
import com.github.veerdone.yblog.cloud.base.Dto.article.CreateArticleDto;
import com.github.veerdone.yblog.cloud.base.Dto.article.UpdateArticleDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleContentController {
    private final ArticleContentService articleContentService;

    public ArticleContentController(ArticleContentService articleContentService) {
        this.articleContentService = articleContentService;
    }

    @PostMapping
    public void create(@RequestBody @Validated CreateArticleDto dto) {
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
