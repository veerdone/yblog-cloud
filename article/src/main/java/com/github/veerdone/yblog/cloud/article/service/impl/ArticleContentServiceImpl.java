package com.github.veerdone.yblog.cloud.article.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleContentMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleContentService;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.article.service.MqProvider;
import com.github.veerdone.yblog.cloud.base.Dto.post.CreateArticleDto;
import com.github.veerdone.yblog.cloud.base.Dto.post.UpdateArticleDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleContent;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.Resource;


@Service
public class ArticleContentServiceImpl implements ArticleContentService {
    @Resource
    private ArticleContentMapper articleContentMapper;

    @Resource
    private ArticleInfoService articleInfoService;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private MqProvider mqProvider;

    @Override
    public void create(CreateArticleDto dto) {
        ArticleInfo articleInfo = ArticleConvert.INSTANCE.toArticleInfo(dto);
        transactionTemplate.executeWithoutResult(status -> {
            ArticleContent articleContent = new ArticleContent();
            articleContent.setContent(dto.getContent());
            articleContentMapper.insert(articleContent);

            articleInfo.setId(articleContent.getId());
            articleInfo.setCreateTime(articleContent.getCreateTime());
            articleInfo.setUpdateTime(articleContent.getUpdateTime());
            articleInfoService.create(articleInfo);
        });

        mqProvider.reviewArticle(articleInfo);
    }

    @Override
    @Transactional
    public void updateById(UpdateArticleDto dto) {
        ArticleInfo articleInfo = ArticleConvert.INSTANCE.toArticleInfo(dto);
        articleInfoService.updateById(articleInfo);

        if (StrUtil.isNotBlank(dto.getContent())) {
            ArticleContent articleContent = new ArticleContent();
            articleContent.setId(dto.getId());
            articleContent.setContent(dto.getContent());

            articleContentMapper.updateById(articleContent);
        }
    }

    @Override
    public ArticleDetailVo getArticleDetailVoById(Long id) {
        ArticleDetailVo articleDetailVo = articleInfoService.getArticleDetailVoById(id);
        ArticleContent articleContent = articleContentMapper.selectById(id);
        articleDetailVo.setContent(articleContent.getContent());

        return articleDetailVo;
    }
}
