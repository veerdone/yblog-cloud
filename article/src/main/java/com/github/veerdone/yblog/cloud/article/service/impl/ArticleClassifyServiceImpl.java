package com.github.veerdone.yblog.cloud.article.service.impl;

import com.github.veerdone.yblog.cloud.article.mapper.ArticleClassifyMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleClassifyService;
import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ArticleClassifyServiceImpl implements ArticleClassifyService {
    @Resource
    private ArticleClassifyMapper articleClassifyMapper;

    @Override
    public void create(ArticleClassify articleClassify) {
        articleClassifyMapper.insert(articleClassify);
    }

    @Override
    public List<ArticleClassify> list() {
        return articleClassifyMapper.selectList(null);
    }
}
