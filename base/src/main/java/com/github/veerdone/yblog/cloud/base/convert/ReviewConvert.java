package com.github.veerdone.yblog.cloud.base.convert;

import com.github.veerdone.yblog.cloud.base.Dto.review.UpdateReviewDto;
import com.github.veerdone.yblog.cloud.base.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReviewConvert {
    ReviewConvert INSTANCE = Mappers.getMapper(ReviewConvert.class);

    Review toReview(UpdateReviewDto dto);
}
