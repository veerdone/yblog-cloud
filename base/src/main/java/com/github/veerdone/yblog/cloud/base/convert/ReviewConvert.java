package com.github.veerdone.yblog.cloud.base.convert;

import com.github.veerdone.yblog.cloud.base.Dto.review.UpdateReviewDto;
import com.github.veerdone.yblog.cloud.base.Vo.ArticleReviewVo;
import com.github.veerdone.yblog.cloud.base.Vo.CommentReviewVo;
import com.github.veerdone.yblog.cloud.base.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewConvert {
    ReviewConvert INSTANCE = Mappers.getMapper(ReviewConvert.class);

    Review toReview(UpdateReviewDto dto);

    ArticleReviewVo toArticleReviewVo(Review review);

    CommentReviewVo toCommentReviewVo(Review review);
}
