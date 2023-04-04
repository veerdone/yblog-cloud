package com.github.veerdone.yblog.cloud.article.controller;

import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.base.Dto.ArticleSearchDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.common.elasticsearch.ElasticUtil;
import com.github.veerdone.yblog.cloud.common.web.result.ListResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleInfoController {
    private final ArticleInfoService articleInfoService;

    public ArticleInfoController(ArticleInfoService articleInfoService) {
        this.articleInfoService = articleInfoService;
    }

    @PostMapping("/list/_common")
    public List<ArticleInfoVo> list(@RequestBody ArticleInfo articleInfo) {
        return articleInfoService.listArticleInfoVo(articleInfo);
    }

    @GetMapping("/search/_common")
    public ListResult<ArticleInfoVo> search(ArticleSearchDto dto) {
        try {
            List<ArticleInfoVo> articleInfoVoList = articleInfoService.searchArticleVo(dto);
            Number total = ElasticUtil.getLocalTotal();

            return ListResult.result(articleInfoVoList, total.longValue());
        } finally {
            ElasticUtil.cleanPage();
        }
    }
}
