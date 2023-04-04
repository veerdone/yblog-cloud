package com.github.veerdone.yblog.cloud.article.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.veerdone.yblog.cloud.article.mapper.ArticleViewHistoryMapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.article.service.ArticleViewHistoryService;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleViewHistoryVo;
import com.github.veerdone.yblog.cloud.base.model.ArticleViewHistory;
import com.github.veerdone.yblog.cloud.common.page.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ArticleViewHistoryServiceImpl implements ArticleViewHistoryService {
    private final ArticleViewHistoryMapper articleViewHistoryMapper;

    private final ArticleInfoService articleInfoService;

    private final TransactionTemplate transactionTemplate;
    public ArticleViewHistoryServiceImpl(ArticleViewHistoryMapper articleViewHistoryMapper, ArticleInfoService articleInfoService,
                                         TransactionTemplate transactionTemplate) {
        this.articleViewHistoryMapper = articleViewHistoryMapper;
        this.articleInfoService = articleInfoService;
        this.transactionTemplate = transactionTemplate;
    }


    @Override
    public void create(ArticleViewHistory articleViewHistory) {
        transactionTemplate.executeWithoutResult(status -> {
            articleViewHistoryMapper.insert(articleViewHistory);
            IncrOrDecrColumnDto dto = new IncrOrDecrColumnDto();
            dto.setItemId(articleViewHistory.getArticleId());
            dto.setColumn("views");
            dto.setNum(1);
            articleInfoService.updateByIncrOrDecrColumnDto(dto);
        });
    }

    @Override
    public void updateById(Long id) {
        ArticleViewHistory articleViewHistory = new ArticleViewHistory();
        articleViewHistory.setArticleId(id);

        articleViewHistoryMapper.updateById(articleViewHistory);
    }

    @Override
    @Page
    public List<ArticleViewHistoryVo> list() {
        LambdaQueryWrapper<ArticleViewHistory> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(ArticleViewHistory::getUpdateTime)
                .eq(ArticleViewHistory::getDeleted, 0);
        List<ArticleViewHistory> articleViewHistoryList = articleViewHistoryMapper.selectList(wrapper);
        if (CollectionUtil.isEmpty(articleViewHistoryList)) {
            return Collections.emptyList();
        }

        List<Long> articleIdList = articleViewHistoryList.stream().map(ArticleViewHistory::getArticleId).toList();
        List<ArticleInfoVo> articleInfoVoList = articleInfoService.getByIds(articleIdList);
        List<ArticleViewHistoryVo> articleViewHistoryVoList = new ArrayList<>(articleViewHistoryList.size());
        for (int i = 0; i < articleInfoVoList.size(); i++) {
            ArticleViewHistoryVo articleViewHistoryVo = new ArticleViewHistoryVo();
            articleViewHistoryVo.setArticleInfoVo(articleInfoVoList.get(i));
            articleViewHistoryVo.setArticleViewHistory(articleViewHistoryList.get(i));
            articleViewHistoryVoList.add(articleViewHistoryVo);
        }

        return articleViewHistoryVoList;
    }
}
