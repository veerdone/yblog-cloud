package com.github.veerdone.yblog.cloud.base.convert;

import com.github.veerdone.yblog.cloud.base.Dto.ArticleDocumentDto;
import com.github.veerdone.yblog.cloud.base.Dto.post.CreateArticleDto;
import com.github.veerdone.yblog.cloud.base.Dto.post.UpdateArticleDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleDetailVo;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleInfoVo;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleConvert {
    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    ArticleInfoVo toArticleInfoVo(ArticleInfo articleInfo);

    ArticleInfoVo toArticleInfoVo(ArticleDocumentDto dto);

    ArticleInfo toArticleInfo(CreateArticleDto dto);

    ArticleInfo toArticleInfo(UpdateArticleDto dto);

    ArticleDetailVo toArticleDetailVo(ArticleInfo articleInfo);

    ArticleDocumentDto toDocumentDto(ArticleInfo articleInfo);
}
