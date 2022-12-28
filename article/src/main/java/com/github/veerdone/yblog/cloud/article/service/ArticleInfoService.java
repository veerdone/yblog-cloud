package com.github.veerdone.yblog.cloud.article.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;

import java.util.List;

public interface ArticleInfoService {
    /**
     * create articleInfo
     * @param articleInfo the articleInfo model
     */
    void create(ArticleInfo articleInfo);

    /**
     * update articleInfo
     * @param articleInfo the articleInfo model
     */
    void updateById(ArticleInfo articleInfo);

    /**
     * update userInfo by wrapper
     * @param wrapper update wrapper
     */
    void updateByWrapper(Wrapper<ArticleInfo> wrapper);

    /**
     * query userInfo by id
     * @param id user id
     * @return the userInfo model
     */
    ArticleInfo getById(Long id);

    /**
     * query articleDetailVo by id
     * @param id article id
     * @return the articleDetailVo model
     */
    ArticleDetailVo getArticleDetailVoById(Long id);

    /**
     * query list of articleInfoVo by articleInfo
     * @param articleInfo the articleInfo model
     * @return list of articleInfoVo
     */
    List<ArticleInfoVo> listArticleInfoVo(ArticleInfo articleInfo);
}
