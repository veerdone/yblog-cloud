package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.article.service.ArticleClassifyService;
import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import com.github.veerdone.yblog.cloud.common.page.PageUtil;
import com.github.veerdone.yblog.cloud.common.response.result.ListResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article/classify")
public class ArticleClassifyController {
    @Resource
    private ArticleClassifyService articleClassifyService;

    @PostMapping("/create")
    public void create(@RequestBody @Validated ArticleClassify classify) {
        articleClassifyService.create(classify);
    }

    @GetMapping("/list")
    public ListResult<ArticleClassify> list() {
        List<ArticleClassify> articleClassifyList = articleClassifyService.list();

        return PageUtil.response(articleClassifyList);
    }
}
