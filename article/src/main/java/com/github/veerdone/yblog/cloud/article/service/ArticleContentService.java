package com.github.veerdone.yblog.cloud.article.service;

import com.github.veerdone.yblog.cloud.base.Dto.post.CreateArticleDto;

public interface ArticleContentService {
    void create(CreateArticleDto dto);
}
