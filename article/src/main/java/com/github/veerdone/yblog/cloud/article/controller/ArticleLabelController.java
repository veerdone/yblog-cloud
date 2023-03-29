package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article/label")
public class ArticleLabelController {
    @Resource
    private ArticleLabelService articleLabelService;

    @PostMapping("/create")
    public void create(@RequestBody @Validated ArticleLabel label) {
        articleLabelService.create(label);
    }

    @GetMapping("/list")
    public List<ArticleLabel> list(@RequestParam(value = "classify_id", required = false) Long classifyId) {
        List<ArticleLabel> articleLabelList = articleLabelService.listByClassifyId(classifyId);

        return articleLabelList;
    }

}
