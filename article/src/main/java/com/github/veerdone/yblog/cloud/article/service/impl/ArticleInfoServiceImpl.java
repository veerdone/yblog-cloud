package com.github.veerdone.yblog.cloud.article.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleInfoMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.aop.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {
    @Resource
    private ArticleInfoMapper articleInfoMapper;

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
            UserInfo userInfo = userClient.getUserInfoById(articleInfoItem.getUserId());
            articleInfoVo.setUserInfo(userInfo);
            articleInfoVoList.add(articleInfoVo);
        }

        return articleInfoVoList;
    }
}
