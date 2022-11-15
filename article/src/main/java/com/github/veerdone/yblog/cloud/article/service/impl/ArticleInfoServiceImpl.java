package com.github.veerdone.yblog.cloud.article.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleInfoMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleClassifyService;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.page.Page;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {
    public static final Logger log = LoggerFactory.getLogger(ArticleInfoServiceImpl.class);

    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Resource
    private ArticleClassifyService articleClassifyService;

    @Resource
    private ArticleLabelService articleLabelService;

    @DubboReference(check = false)
    private UserClient userClient;

    @Override
    public void create(ArticleInfo articleInfo) {
        articleInfoMapper.insert(articleInfo);
        // todo 调用审核服务
    }

    @Override
    public void updateById(ArticleInfo articleInfo) {
        articleInfo.setStatus(0);
        articleInfoMapper.updateById(articleInfo);

        //todo 调用审核服务
    }

    @Override
    public ArticleDetailVo getArticleDetailVoById(Long id) {
        ArticleInfo articleInfo = articleInfoMapper.selectById(id);
        ArticleDetailVo articleDetailVo = ArticleConvert.INSTANCE.toArticleDetailVo(articleInfo);

        articleDetailVo.setArticleClassify(articleClassifyService.getById(articleInfo.getClassify()));
        List<ArticleLabel> articleLabelList = getArticleLabelListByIds(articleInfo.getLabel());
        articleDetailVo.setArticleLabelList(articleLabelList);

        return articleDetailVo;
    }

    @Page
    @Override
    public List<ArticleInfoVo> listArticleInfoVo(ArticleInfo articleInfo) {
        LambdaQueryWrapper<ArticleInfo> wrapper = new LambdaQueryWrapper<>();
        if (Objects.isNull(articleInfo)) {
            wrapper.eq(ArticleInfo::getStatus, 1);
        }
        List<ArticleInfo> articleInfoList = articleInfoMapper.selectList(wrapper);
        if (CollectionUtil.isEmpty(articleInfoList)) {
            return Collections.emptyList();
        }

        List<ArticleInfoVo> articleInfoVoList = new ArrayList<>(articleInfoList.size());
        for (ArticleInfo articleInfoItem : articleInfoList) {
            ArticleInfoVo articleInfoVo = ArticleConvert.INSTANCE.toArticleInfoVo(articleInfoItem);
            UserInfo userInfo = userClient.getUserInfoById(articleInfoItem.getUserId());
            articleInfoVo.setUserInfo(userInfo);

            ArticleClassify articleClassify = articleClassifyService.getById(articleInfoItem.getClassify());
            articleInfoVo.setArticleClassify(articleClassify);

            List<ArticleLabel> articleLabelList = getArticleLabelListByIds(articleInfoItem.getLabel());
            articleInfoVo.setArticleLabelList(articleLabelList);

            articleInfoVoList.add(articleInfoVo);
        }

        return articleInfoVoList;
    }


    private List<ArticleLabel> getArticleLabelListByIds(List<Long> ids) {
        List<ArticleLabel> articleLabelList = new ArrayList<>(ids.size());
        for (Long id : ids) {
            articleLabelList.add(articleLabelService.getById(id));
        }

        return articleLabelList;
    }
}
