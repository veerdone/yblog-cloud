package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article/label")
public class ArticleLabelController {
    private final ArticleLabelService articleLabelService;

    public ArticleLabelController(ArticleLabelService articleLabelService) {
        this.articleLabelService = articleLabelService;
    }

    @PostMapping
    public void create(@RequestBody @Validated ArticleLabel label) {
        articleLabelService.create(label);
    }

    @GetMapping
    public List<ArticleLabel> list(@RequestParam(value = "classify_id", required = false) Long classifyId) {
        return articleLabelService.listByClassifyId(classifyId);
    }

}
