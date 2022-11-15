package com.github.veerdone.yblog.cloud.base.convert;

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

    ArticleInfo toArticleInfo(CreateArticleDto dto);

    ArticleInfo toArticleInfo(UpdateArticleDto dto);

    ArticleDetailVo toArticleDetailVo(ArticleInfo articleInfo);
}
