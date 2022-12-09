package com.github.veerdone.yblog.cloud.base.convert;

import com.github.veerdone.yblog.cloud.base.Dto.thumbsUp.ThumbsUpDto;
import com.github.veerdone.yblog.cloud.base.model.ThumbsUp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InteractConvert {
    InteractConvert INSTANCE = Mappers.getMapper(InteractConvert.class);

    ThumbsUp thumbsUp(ThumbsUpDto dto);
}
