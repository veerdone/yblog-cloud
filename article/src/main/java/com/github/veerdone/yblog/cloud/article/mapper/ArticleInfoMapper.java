package com.github.veerdone.yblog.cloud.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;

import java.util.List;

public interface ArticleInfoMapper extends BaseMapper<ArticleInfo> {
    List<ArticleInfo> listByEntity(ArticleInfo entity);
}
