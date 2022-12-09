package com.github.veerdone.yblog.cloud.article.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;

import java.util.List;

public interface ArticleInfoService {
    void create(ArticleInfo articleInfo);

    void updateById(ArticleInfo articleInfo);

    void updateByWrapper(Wrapper<ArticleInfo> wrapper);

    ArticleInfo getById(Long id);

    ArticleDetailVo getArticleDetailVoById(Long id);

    List<ArticleInfoVo> listArticleInfoVo(ArticleInfo articleInfo);
}
