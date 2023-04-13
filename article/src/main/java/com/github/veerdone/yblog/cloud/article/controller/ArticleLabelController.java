package com.github.veerdone.yblog.cloud.article.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.base.Dto.article.CreateArticleLabelDto;
import com.github.veerdone.yblog.cloud.base.Dto.article.UpdateArticleLabelDto;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/article/label")
public class ArticleLabelController {
    private final ArticleLabelService articleLabelService;

    public ArticleLabelController(ArticleLabelService articleLabelService) {
        this.articleLabelService = articleLabelService;
    }

    @PostMapping
    public void create(@RequestBody @Validated CreateArticleLabelDto dto) {
        Object role = StpUtil.getSession().get("role");
        if (Objects.equals(2, role)) {
            ArticleLabel label = ArticleConvert.INSTANCE.toArticleLabel(dto);
            articleLabelService.create(label);
        }
    }

    @PutMapping
    public void updateById(@RequestBody @Validated UpdateArticleLabelDto dto) {
        Object role = StpUtil.getSession().get("role");
        if (Objects.equals(2, role)) {
            ArticleLabel label = ArticleConvert.INSTANCE.toArticleLabel(dto);
            articleLabelService.updateById(label);
        }
    }

    @GetMapping
    public List<ArticleLabel> list(@RequestParam(value = "classify_id", required = false) Long classifyId) {
        return articleLabelService.listByClassifyId(classifyId);
    }

}
