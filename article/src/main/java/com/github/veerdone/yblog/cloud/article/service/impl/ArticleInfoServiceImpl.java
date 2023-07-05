package com.github.veerdone.yblog.cloud.article.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleInfoMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleClassifyService;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.article.service.ArticleLabelService;
import com.github.veerdone.yblog.cloud.article.service.ElasticService;
import com.github.veerdone.yblog.cloud.base.Dto.ArticleDocumentDto;
import com.github.veerdone.yblog.cloud.base.Dto.ArticleSearchDto;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.api.user.QueryUserByIdReq;
import com.github.veerdone.yblog.cloud.base.api.user.QueryUserByIdResp;
import com.github.veerdone.yblog.cloud.base.api.user.QueryUserByIdsReq;
import com.github.veerdone.yblog.cloud.base.api.user.QueryUserByIdsResp;
import com.github.veerdone.yblog.cloud.base.api.user.UserClientGrpc;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.convert.UserConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.common.constant.CacheKey;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import com.github.veerdone.yblog.cloud.common.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ArticleInfoServiceImpl implements ArticleInfoService {
    public static final Logger log = LoggerFactory.getLogger(ArticleInfoServiceImpl.class);

    private final ArticleInfoMapper articleInfoMapper;

    private final ArticleClassifyService articleClassifyService;

    private final ArticleLabelService articleLabelService;

    private final RedisTemplate<String, Object> redisTemplate;

    private final ElasticService elasticService;

    private final UserClientGrpc.UserClientBlockingStub userClientBlockingStub;

    public ArticleInfoServiceImpl(ArticleInfoMapper articleInfoMapper, ArticleClassifyService articleClassifyService,
                                  ArticleLabelService articleLabelService, RedisTemplate<String, Object> redisTemplate,
                                  ElasticService elasticService, UserClientGrpc.UserClientBlockingStub userClientBlockingStub) {
        this.articleInfoMapper = articleInfoMapper;
        this.articleClassifyService = articleClassifyService;
        this.articleLabelService = articleLabelService;
        this.redisTemplate = redisTemplate;
        this.elasticService = elasticService;
        this.userClientBlockingStub = userClientBlockingStub;
    }

    @Override
    public void create(ArticleInfo articleInfo) {
        articleInfoMapper.insert(articleInfo);
    }

    @Override
    public void updateById(ArticleInfo articleInfo) {
        articleInfo.setStatus(0);
        articleInfoMapper.updateById(articleInfo);
        String cacheKey = CacheKey.ARTICLE_INFO_QUERY_BY_ID + articleInfo.getId();
        log.debug("delete article_info cache by cache_key={}", cacheKey);
        redisTemplate.delete(cacheKey);
    }

    @Override
    public void updateByIncrOrDecrColumnDto(IncrOrDecrColumnDto dto) {
        LambdaUpdateWrapper<ArticleInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ArticleInfo::getId, dto.getItemId())
                .setSql(StrUtil.format("{}={}+{}", dto.getColumn(), dto.getColumn(), dto.getNum()));
        articleInfoMapper.update(null, wrapper);
        String cacheKey = CacheKey.ARTICLE_INFO_QUERY_BY_ID + dto.getItemId();
        log.debug("delete article_info cache by cache_key={}", cacheKey);
        redisTemplate.delete(cacheKey);
    }

    @Override
    public void deleteById(Long id) {
        long userId = StpUtil.getLoginIdAsLong();
        ArticleInfo articleInfo = this.getById(id);
        if (Objects.equals(articleInfo.getUserId(), userId)) {
            articleInfo.setDeleted(1);
            articleInfoMapper.updateById(articleInfo);
        }
    }

    @Override
    public ArticleInfo getById(Long id) {
        String cacheKey = CacheKey.ARTICLE_INFO_QUERY_BY_ID + id;
        log.debug("set article_info cache by cache_key={}", cacheKey);
        Object cache = redisTemplate.opsForValue().get(cacheKey);
        if (Objects.nonNull(cache)) {
            return (ArticleInfo) cache;
        }
        ArticleInfo articleInfo = articleInfoMapper.selectById(id);
        redisTemplate.opsForValue().set(cacheKey, articleInfo, 30, TimeUnit.MINUTES);

        return articleInfo;
    }

    @Override
    public ArticleDetailVo getArticleDetailVoById(Long id) {
        ArticleInfo articleInfo = this.getById(id);
        if (Objects.isNull(articleInfo) || !Objects.equals(articleInfo.getStatus(), StatusConstant.REVIEW_THROUGH) ||
                Objects.equals(articleInfo.getDeleted(), 1)) {
            throw new ServiceException(ServiceExceptionEnum.ARTICLE_NOT_EXIST);
        }

        ArticleDetailVo articleDetailVo = ArticleConvert.INSTANCE.toArticleDetailVo(articleInfo);

        articleDetailVo.setArticleClassify(articleClassifyService.getById(articleInfo.getClassify()));
        List<ArticleLabel> articleLabelList = articleLabelService.getByIds(articleInfo.getLabel());
        articleDetailVo.setArticleLabelList(articleLabelList);

        QueryUserByIdResp queryUserByIdResp = userClientBlockingStub.queryById(QueryUserByIdReq.newBuilder().setId(articleInfo.getUserId()).build());
        articleDetailVo.setUserInfo(UserConvert.INSTANCE.toUserInfo(queryUserByIdResp.getUserInfo()));

        return articleDetailVo;
    }

    @Page
    @Override
    public List<ArticleInfoVo> listArticleInfoVo(ArticleInfo entity) {
        List<ArticleInfo> articleInfoList = articleInfoMapper.listByEntity(entity);
        if (CollectionUtil.isEmpty(articleInfoList)) {
            return Collections.emptyList();
        }

        return getArticleInfoVos(articleInfoList);
    }

    @Override
    public List<ArticleInfoVo> searchArticleVo(ArticleSearchDto dto) {
        List<ArticleDocumentDto> articleDocumentDtoList = elasticService.search(dto);

        List<ArticleInfoVo> articleInfoVoList = new ArrayList<>(articleDocumentDtoList.size());
        List<Long> userIds = articleDocumentDtoList.stream().map(ArticleDocumentDto::getUserId).collect(Collectors.toList());

        QueryUserByIdsResp queryUserByIdsResp = userClientBlockingStub.queryByIds(QueryUserByIdsReq.newBuilder().addAllIds(userIds).build());
        List<com.github.veerdone.yblog.cloud.base.api.user.UserInfo> respUserInfosList = queryUserByIdsResp.getUserInfosList();
        List<UserInfo> userInfoList = new ArrayList<>(respUserInfosList.size());
        respUserInfosList.forEach(userInfo -> userInfoList.add(UserConvert.INSTANCE.toUserInfo(userInfo)));

        for (int i = 0; i < articleDocumentDtoList.size(); i++) {
            ArticleDocumentDto articleDocumentDto = articleDocumentDtoList.get(i);
            ArticleInfoVo articleInfoVo = ArticleConvert.INSTANCE.toArticleInfoVo(articleDocumentDto);

            setArticleInfoVoField(articleInfoVo, userInfoList.get(i), articleDocumentDto.getClassify(), articleDocumentDto.getLabel());

            articleInfoVoList.add(articleInfoVo);
        }

        return articleInfoVoList;
    }

    @Override
    public List<ArticleInfoVo> getByIds(List<Long> ids) {
        LambdaQueryWrapper<ArticleInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ArticleInfo::getId, ids);
        List<ArticleInfo> articleInfoList = articleInfoMapper.selectList(wrapper);
        if (CollectionUtil.isEmpty(articleInfoList)) {
            return Collections.emptyList();
        }

        return getArticleInfoVos(articleInfoList);
    }

    private List<ArticleInfoVo> getArticleInfoVos(List<ArticleInfo> articleInfoList) {
        List<Long> userIds = articleInfoList.stream().map(ArticleInfo::getUserId).collect(Collectors.toList());

        QueryUserByIdsResp queryUserByIdsResp = userClientBlockingStub.queryByIds(QueryUserByIdsReq.newBuilder().addAllIds(userIds).build());
        List<com.github.veerdone.yblog.cloud.base.api.user.UserInfo> respUserInfosList = queryUserByIdsResp.getUserInfosList();
        List<UserInfo> userInfoList = new ArrayList<>(respUserInfosList.size());
        respUserInfosList.forEach(userInfo -> userInfoList.add(UserConvert.INSTANCE.toUserInfo(userInfo)));

        List<ArticleInfoVo> articleInfoVoList = new ArrayList<>(articleInfoList.size());

        for (int i = 0; i < articleInfoList.size(); i++) {
            ArticleInfo articleInfo = articleInfoList.get(i);
            ArticleInfoVo articleInfoVo = ArticleConvert.INSTANCE.toArticleInfoVo(articleInfo);
            setArticleInfoVoField(articleInfoVo, userInfoList.get(i), articleInfo.getClassify(), articleInfo.getLabel());
            Optional.ofNullable(StpUtil.getLoginIdDefaultNull()).ifPresent(userId -> {
                String cacheKey = CacheKey.USER_ARTICLE_THUMBS_UP + userId;
                if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(cacheKey, articleInfo.getId()))) {
                    articleInfoVo.setIsLike(true);
                }
            });
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
