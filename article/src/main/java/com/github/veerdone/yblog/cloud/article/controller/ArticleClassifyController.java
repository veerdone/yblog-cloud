package com.github.veerdone.yblog.cloud.article.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.veerdone.yblog.cloud.article.service.ArticleClassifyService;
import com.github.veerdone.yblog.cloud.base.Dto.article.CreateArticleClassifyDto;
import com.github.veerdone.yblog.cloud.base.Dto.article.UpdateArticleClassifyDto;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/article/classify")
public class ArticleClassifyController {
    private final ArticleClassifyService articleClassifyService;

    public ArticleClassifyController(ArticleClassifyService articleClassifyService) {
        this.articleClassifyService = articleClassifyService;
    }

    @PostMapping
    public void create(@RequestBody @Validated CreateArticleClassifyDto dto) {
        Object role = StpUtil.getSession().get("role");
        if (Objects.equals(2, role)) {
            ArticleClassify classify = ArticleConvert.INSTANCE.toArticleclassify(dto);
            articleClassifyService.create(classify);
        }
    }

    @PutMapping
    public void updateById(@RequestBody @Validated UpdateArticleClassifyDto dto) {
        Object role = StpUtil.getSession().get("role");
        if (Objects.equals(2, role)) {
            ArticleClassify classify = ArticleConvert.INSTANCE.toArticleClassify(dto);
            articleClassifyService.updateById(classify);
        }
    }

    @GetMapping
    public List<ArticleClassify> list() {
        return articleClassifyService.list();
    }
}
