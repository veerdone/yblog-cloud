package com.github.veerdone.yblog.cloud.article.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.base.Dto.ArticleSearchDto;
import com.github.veerdone.yblog.cloud.base.Dto.article.QueryArticleInfoDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import com.github.veerdone.yblog.cloud.common.elasticsearch.ElasticUtil;
import com.github.veerdone.yblog.cloud.common.web.result.ListResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/article")
public class ArticleInfoController {
    private final ArticleInfoService articleInfoService;

    public ArticleInfoController(ArticleInfoService articleInfoService) {
        this.articleInfoService = articleInfoService;
    }

    @PostMapping("/list")
    public List<ArticleInfoVo> list(@RequestBody QueryArticleInfoDto dto) {
        ArticleInfo articleInfo = ArticleConvert.INSTANCE.toArticleInfo(dto);
        Integer status = StatusConstant.REVIEW_THROUGH;
        if (StpUtil.isLogin() && Objects.equals(2, StpUtil.getSession().get("role"))) {
            status = dto.getStatus();
        }
        articleInfo.setStatus(status);

        return articleInfoService.listArticleInfoVo(articleInfo);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        articleInfoService.deleteById(id);
    }

    @GetMapping("/search")
    public ListResult<ArticleInfoVo> search(ArticleSearchDto dto) {
        try {
            List<ArticleInfoVo> articleInfoVoList = articleInfoService.searchArticleVo(dto);
            Number total = ElasticUtil.getLocalTotal();

            return ListResult.result(articleInfoVoList, total.longValue());
        } finally {
            ElasticUtil.cleanThreadLocal();
        }
    }
}
