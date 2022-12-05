package com.github.veerdone.yblog.cloud.base.convert;

import com.github.veerdone.yblog.cloud.base.Dto.review.ArticleReviewFailDto;
import com.github.veerdone.yblog.cloud.base.Dto.review.ArticleReviewThroughDto;
import com.github.veerdone.yblog.cloud.base.model.ArticleReview;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewConvert {
    ReviewConvert INSTANCE = Mappers.getMapper(ReviewConvert.class);

    ArticleReview toArticleReview(ArticleReviewThroughDto dto);

    ArticleReview toArticleReview(ArticleReviewFailDto dto);
}
