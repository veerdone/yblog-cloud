package com.github.veerdone.yblog.cloud.article.api;

import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.base.client.ArticleClient;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@DubboService
@Service
public class ArticleClientImpl implements ArticleClient {
    @Resource
    private ArticleInfoService articleInfoService;

    @Override
    public void updateById(ArticleInfo articleInfo) {
        articleInfoService.updateById(articleInfo);
    }
}
