package com.github.veerdone.yblog.cloud.article.api;

import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.article.service.ElasticService;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.client.ArticleClient;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

@DubboService
@Service
public class ArticleClientImpl implements ArticleClient {
    private final ArticleInfoService articleInfoService;

    private final ElasticService elasticService;

    public ArticleClientImpl(ArticleInfoService articleInfoService, ElasticService elasticService) {
        this.articleInfoService = articleInfoService;
        this.elasticService = elasticService;
    }

    @Override
    public ArticleInfo getById(Long id) {
        return articleInfoService.getById(id);
    }

    @Override
    public void updateById(ArticleInfo articleInfo) {
        articleInfoService.updateById(articleInfo);
    }

    @Override
    public ArticleInfo updateStatusAndGet(Long id, Integer status) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setId(id);
        articleInfo.setStatus(status);
        articleInfoService.updateById(articleInfo);

        articleInfo = articleInfoService.getById(id);
        if (status.equals(StatusConstant.REVIEW_THROUGH)) {
            elasticService.saveDocument(articleInfo);
        }

        return articleInfo;
    }

    @Override
    public void incrOrDecrColumn(IncrOrDecrColumnDto dto) {
        articleInfoService.updateByIncrOrDecrColumnDto(dto);
    }

    @Override
    public void updateStatusAndIncrOrDecrColumn(Long id, Integer status, IncrOrDecrColumnDto dto) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setId(id);
        articleInfo.setStatus(status);
        articleInfoService.updateById(articleInfo);

        this.incrOrDecrColumn(dto);
    }

}
