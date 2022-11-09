package com.github.veerdone.yblog.cloud.article.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleInfoMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleClassifyService;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import com.github.veerdone.yblog.cloud.base.model.UserData;
import com.github.veerdone.yblog.cloud.common.aop.Page;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {
    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Resource
    private ArticleClassifyService articleClassifyService;

    @Resource
    private ArticleLabelService articleLabelService;

    @DubboReference
    private UserClient userClient;

    @Page
    @Override
    public List<ArticleInfoVo> listArticleInfoVo(ArticleInfo articleInfo) {
        List<ArticleInfo> articleInfoList = articleInfoMapper.selectList(null);
        if (CollectionUtil.isEmpty(articleInfoList)) {
            return Collections.emptyList();
        }

        List<ArticleInfoVo> articleInfoVoList = new ArrayList<>(articleInfoList.size());
        for (ArticleInfo articleInfoItem : articleInfoList) {
            ArticleInfoVo articleInfoVo = ArticleConvert.INSTANCE.toVo(articleInfoItem);
            UserData userData = userClient.getUserDataById(articleInfoItem.getUserId());
            articleInfoVo.setUserData(userData);

            ArticleClassify articleClassify = articleClassifyService.getById(articleInfoItem.getClassify());
            articleInfoVo.setArticleClassify(articleClassify);

            List<Long> label = articleInfoItem.getLabel();
            List<ArticleLabel> articleLabelList = new ArrayList<>(label.size());
            for (Long labelId : label) {
                articleLabelList.add(articleLabelService.getById(labelId));
            }
            articleInfoVo.setArticleLabelList(articleLabelList);

            articleInfoVoList.add(articleInfoVo);
        }

        return articleInfoVoList;
    }
}
