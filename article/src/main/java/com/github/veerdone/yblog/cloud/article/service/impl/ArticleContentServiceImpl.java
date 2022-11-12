package com.github.veerdone.yblog.cloud.article.service.impl;

import com.github.veerdone.yblog.cloud.article.mapper.ArticleContentMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleContentService;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.base.Dto.post.CreateArticleDto;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleContent;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service
public class ArticleContentServiceImpl implements ArticleContentService {
    @Resource
    private ArticleContentMapper articleContentMapper;

    @Resource
    private ArticleInfoService articleInfoService;

    @Override
    @Transactional
    public void create(CreateArticleDto dto) {
        ArticleContent articleContent = new ArticleContent();
        articleContent.setContent(dto.getContent());
        articleContentMapper.insert(articleContent);

        ArticleInfo articleInfo = ArticleConvert.INSTANCE.toArticleInfo(dto);
        articleInfo.setId(articleContent.getId());
        articleInfoService.create(articleInfo);

        //todo 调用审核服务
    }
}
