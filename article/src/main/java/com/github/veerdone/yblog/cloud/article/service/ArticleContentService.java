package com.github.veerdone.yblog.cloud.article.service;

import com.github.veerdone.yblog.cloud.base.Dto.post.CreateArticleDto;
import com.github.veerdone.yblog.cloud.base.Dto.post.UpdateArticleDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;

public interface ArticleContentService {
    void create(CreateArticleDto dto);

    void updateById(UpdateArticleDto dto);

    ArticleDetailVo getArticleDetailVoById(Long id);
}
