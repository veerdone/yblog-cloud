package com.github.veerdone.yblog.cloud.base.convert;

import com.github.veerdone.yblog.cloud.base.Dto.user.UpdateUserInfoDto;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import org.mapstruct.factory.Mappers;

public interface UserConvert {
    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserInfo toUserInfo(UpdateUserInfoDto dto);
}
