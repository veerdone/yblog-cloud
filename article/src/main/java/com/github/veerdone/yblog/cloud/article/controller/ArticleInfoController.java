package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleInfoController {
    @Resource
    private ArticleInfoService articleInfoService;

    @PostMapping("/list")
    public List<ArticleInfoVo> list() {
        return articleInfoService.listArticleInfoVo(null);
    }
}
