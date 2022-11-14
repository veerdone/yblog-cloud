package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.common.page.PageUtil;
import com.github.veerdone.yblog.cloud.common.response.result.ListResult;
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
    public ListResult<ArticleInfoVo> list() {
        List<ArticleInfoVo> articleInfoVoList = articleInfoService.listArticleInfoVo(null);
        return PageUtil.response(articleInfoVoList);
    }
}
