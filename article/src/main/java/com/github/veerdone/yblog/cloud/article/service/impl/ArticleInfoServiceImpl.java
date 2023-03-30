package com.github.veerdone.yblog.cloud.article.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleInfoMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleClassifyService;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.article.service.ElasticService;
import com.github.veerdone.yblog.cloud.base.Dto.ArticleDocumentDto;
import com.github.veerdone.yblog.cloud.base.Dto.ArticleSearchDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.client.UserClient;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.common.page.Page;
import com.github.veerdone.yblog.cloud.common.redis.RecordUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {
    public static final Logger log = LoggerFactory.getLogger(ArticleInfoServiceImpl.class);

    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Resource
    private ArticleClassifyService articleClassifyService;

    @Resource
    private ArticleLabelService articleLabelService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ElasticService elasticService;

    @DubboReference
    private UserClient userClient;

    @Override
    public void create(ArticleInfo articleInfo) {
        articleInfoMapper.insert(articleInfo);
    }

    @Override
    public void updateById(ArticleInfo articleInfo) {
        articleInfo.setStatus(0);
        articleInfoMapper.updateById(articleInfo);
    }

    @Override
    public void updateByWrapper(Wrapper<ArticleInfo> wrapper) {
        articleInfoMapper.update(null, wrapper);
    }

    @Override
    public ArticleInfo getById(Long id) {
        return articleInfoMapper.selectById(id);
    }

    @Override
    public ArticleDetailVo getArticleDetailVoById(Long id) {
        ArticleInfo articleInfo = articleInfoMapper.selectById(id);
        ArticleDetailVo articleDetailVo = ArticleConvert.INSTANCE.toArticleDetailVo(articleInfo);

        articleDetailVo.setArticleClassify(articleClassifyService.getById(articleInfo.getClassify()));
        List<ArticleLabel> articleLabelList = articleLabelService.getByIds(articleInfo.getLabel());
        articleDetailVo.setArticleLabelList(articleLabelList);

        UserInfo userInfo = userClient.getUserInfoById(articleInfo.getUserId());
        articleDetailVo.setUserInfo(userInfo);

        return articleDetailVo;
    }

    @Page
    @Override
    public List<ArticleInfoVo> listArticleInfoVo(ArticleInfo articleInfo) {
        articleInfo.setStatus(1);
        List<ArticleInfo> articleInfoList = articleInfoMapper.listByEntity(articleInfo);
        if (CollectionUtil.isEmpty(articleInfoList)) {
            return Collections.emptyList();
        }

        List<Long> userIds = articleInfoList.stream().map(ArticleInfo::getUserId).collect(Collectors.toList());
        List<UserInfo> userInfoList = userClient.getUserInfoByIds(userIds);

        List<ArticleInfoVo> articleInfoVoList = new ArrayList<>(articleInfoList.size());
        for (int i = 0; i < articleInfoList.size(); i++) {
            ArticleInfo articleInfoItem = articleInfoList.get(i);
            ArticleInfoVo articleInfoVo = ArticleConvert.INSTANCE.toArticleInfoVo(articleInfoItem);

            setArticleInfoVoField(articleInfoVo, userInfoList.get(i), articleInfoItem.getClassify(), articleInfoItem.getLabel());

            articleInfoVoList.add(articleInfoVo);
        }

        return articleInfoVoList;
    }

    @Override
    public List<ArticleInfoVo> searchArticleVo(ArticleSearchDto dto) {
        List<ArticleDocumentDto> articleDocumentDtoList = elasticService.search(dto);

        List<ArticleInfoVo> articleInfoVoList = new ArrayList<>(articleDocumentDtoList.size());
        List<Long> userIds = articleDocumentDtoList.stream().map(ArticleDocumentDto::getUserId).collect(Collectors.toList());
        List<UserInfo> userInfoList = userClient.getUserInfoByIds(userIds);

        for (int i = 0; i < articleDocumentDtoList.size(); i++) {
            ArticleDocumentDto articleDocumentDto = articleDocumentDtoList.get(i);
            ArticleInfoVo articleInfoVo = ArticleConvert.INSTANCE.toArticleInfoVo(articleDocumentDto);

            setArticleInfoVoField(articleInfoVo, userInfoList.get(i), articleDocumentDto.getClassify(), articleDocumentDto.getLabel());

            articleInfoVoList.add(articleInfoVo);
        }

        return articleInfoVoList;
    }

    private void setArticleInfoVoField(ArticleInfoVo articleInfoVo, UserInfo userInfo, Long classifyId, List<Long> labelIdList) {
        articleInfoVo.setUserInfo(userInfo);

        ArticleClassify articleClassify = articleClassifyService.getById(classifyId);
        articleInfoVo.setArticleClassify(articleClassify);

        List<ArticleLabel> articleLabelList = articleLabelService.getByIds(labelIdList);
        articleInfoVo.setArticleLabelList(articleLabelList);
    }

}
