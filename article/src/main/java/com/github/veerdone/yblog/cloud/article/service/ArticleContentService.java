package com.github.veerdone.yblog.cloud.article.service;

import com.github.veerdone.yblog.cloud.base.Dto.post.CreateArticleDto;
import com.github.veerdone.yblog.cloud.base.Dto.post.UpdateArticleDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import com.github.veerdone.yblog.cloud.base.model.ArticleContent;

public interface ArticleContentService {
    /**
     * create articleContent by dto
     * @param dto the createArticleDto model
     */
    void create(CreateArticleDto dto);

    ArticleContent getById(Long id);

    /**
     * update article by dto
     * @param dto the updateArticleDto model
     */
    void updateById(UpdateArticleDto dto);

    /**
     * query articleDetailVo by id
     * @param id article id
     * @return the articleDetailVo model
     */
    ArticleDetailVo getArticleDetailVoById(Long id);
}
