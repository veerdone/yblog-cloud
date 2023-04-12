package com.github.veerdone.yblog.cloud.article.service;

import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;

import java.util.List;

public interface ArticleClassifyService {
    /**
     * create articleClassify
     * @param articleClassify the articleClassify model
     */
    void create(ArticleClassify articleClassify);

    void updateById(ArticleClassify articleClassify);

    /**
     * query articleClassify by id
     * @param id classify id
     * @return the articleClassify model
     */
    ArticleClassify getById(Long id);

    /**
     * query list of articleClassify
     * @return list of articleClassify
     */
    List<ArticleClassify> list();
}
