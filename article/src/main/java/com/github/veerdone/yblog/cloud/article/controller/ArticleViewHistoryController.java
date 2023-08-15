package com.github.veerdone.yblog.cloud.article.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.veerdone.yblog.cloud.article.service.ArticleViewHistoryService;
import com.github.veerdone.yblog.cloud.base.Dto.article.ListArticleViewHistoryDto;
import com.github.veerdone.yblog.cloud.base.Dto.article.UpdateArticleViewHistoryDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleViewHistoryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article/history")
public class ArticleViewHistoryController {
    private final ArticleViewHistoryService articleViewHistoryService;

    public ArticleViewHistoryController(ArticleViewHistoryService articleViewHistoryService) {
        this.articleViewHistoryService = articleViewHistoryService;
    }

    @PutMapping
    public void updateViewTime(@RequestBody UpdateArticleViewHistoryDto dto) {
        articleViewHistoryService.updateViewTime(dto);
    }

    @GetMapping
    public List<ArticleViewHistoryVo> list(ListArticleViewHistoryDto dto) {
        long id = StpUtil.getLoginIdAsLong();
        dto.setUserId(id);

        return articleViewHistoryService.list(dto);
    }
}
