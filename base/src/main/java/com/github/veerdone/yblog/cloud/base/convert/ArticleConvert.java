package com.github.veerdone.yblog.cloud.base.convert;

import com.github.veerdone.yblog.cloud.base.Dto.ArticleDocumentDto;
import com.github.veerdone.yblog.cloud.base.Dto.article.*;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.model.ArticleClassify;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.base.model.ArticleLabel;
import com.github.veerdone.yblog.cloud.base.model.ArticleViewHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleConvert {
    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    ArticleInfoVo toArticleInfoVo(ArticleInfo articleInfo);

    ArticleInfoVo toArticleInfoVo(ArticleDocumentDto dto);

    ArticleInfo toArticleInfo(CreateArticleDto dto);

    ArticleInfo toArticleInfo(UpdateArticleDto dto);

    ArticleInfo toArticleInfo(QueryArticleInfoDto dto);

    ArticleInfo toArticleInfo(com.github.veerdone.yblog.cloud.base.api.article.ArticleInfo articleInfo);

    ArticleViewHistory toArticleViewHistory(CreateArticleViewHistoryDto dto);

    ArticleDetailVo toArticleDetailVo(ArticleInfo articleInfo);

    ArticleDocumentDto toDocumentDto(ArticleInfo articleInfo);

    ArticleClassify toArticleclassify(CreateArticleClassifyDto dto);

    ArticleClassify toArticleClassify(UpdateArticleClassifyDto dto);

    ArticleLabel toArticleLabel(CreateArticleLabelDto dto);

    ArticleLabel toArticleLabel(UpdateArticleLabelDto dto);

    com.github.veerdone.yblog.cloud.base.api.article.ArticleInfo toArticle(ArticleInfo articleInfo);
}
