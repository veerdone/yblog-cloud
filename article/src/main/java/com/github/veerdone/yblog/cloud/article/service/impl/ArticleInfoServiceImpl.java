package com.github.veerdone.yblog.cloud.article.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleInfoMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleClassifyService;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.client.RpcResult;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.aop.Page;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {
    public static final Logger log = LoggerFactory.getLogger(ArticleInfoServiceImpl.class);

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
            UserInfo userInfo = userClient.getUserInfoById(articleInfoItem.getUserId()).getData();
            articleInfoVo.setUserInfo(userInfo);

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

    @Override
    public void test() {
        RpcResult<UserInfo> userInfoById = userClient.getUserInfoById(1L);
        log.info("", userInfoById.getBizException());
    }
}
