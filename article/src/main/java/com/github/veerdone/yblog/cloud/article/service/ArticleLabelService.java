package com.github.veerdone.yblog.cloud.article.service;

import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;

import java.util.List;

public interface ArticleLabelService {
    /**
     * create articleLabel
     * @param articleLabel the articleLabel model
     */
    void create(ArticleLabel articleLabel);

    /**
     * query articleLabel by id
     * @param id articleLabel id
     * @return the articleLabel model
     */
    ArticleLabel getById(Long id);

    List<ArticleLabel> getByIds(List<Long> ids);

    /**
     * query list of articleClassify by id
     * @param classifyId classify id
     * @return list of articleClassify
     */
    List<ArticleLabel> listByClassifyId(Long classifyId);
}
