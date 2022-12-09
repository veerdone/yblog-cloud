package com.github.veerdone.yblog.cloud.article.api;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.client.ArticleClient;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@DubboService
@Service
public class ArticleClientImpl implements ArticleClient {
    @Resource
    private ArticleInfoService articleInfoService;

    @Override
    public ArticleInfo getById(Long id) {
        return articleInfoService.getById(id);
    }

    @Override
    public void updateById(ArticleInfo articleInfo) {
        articleInfoService.updateById(articleInfo);
    }

    @Override
    public void incrOrDecrColumn(IncrOrDecrColumnDto dto) {
        LambdaUpdateWrapper<ArticleInfo> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ArticleInfo::getId, dto.getItemId())
                .setSql(StrUtil.format("{}={}+{}", dto.getColumn(), dto.getColumn(), dto.getNum()));
        articleInfoService.updateByWrapper(wrapper);
    }

}
