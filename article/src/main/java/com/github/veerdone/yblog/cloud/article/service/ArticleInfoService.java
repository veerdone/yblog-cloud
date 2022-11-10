package com.github.veerdone.yblog.cloud.article.service;

import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;

import java.util.List;

public interface ArticleInfoService {

    List<ArticleInfoVo> listArticleInfoVo(ArticleInfo articleInfo);

    void test();
}
