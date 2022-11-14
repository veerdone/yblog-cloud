package com.github.veerdone.yblog.cloud.base.Vo;

import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;

import java.util.List;

public class ArticleDetailVo {
    private Long id;

    private String title;

    private String content;

    private Integer views;

    private Integer collection;

    private Integer likes;

    private Integer status;

    private Integer comments;

    private Long userId;

    private Long createTime;

    private ArticleClassify articleClassify;

    private List<ArticleLabel> articleLabelList;

}
