package com.github.veerdone.yblog.cloud.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.Dto.user.UpdateUserInfoDto;
import com.github.veerdone.yblog.cloud.base.model.UserData;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;

import java.util.List;

public interface UserInfoService {
    /**
     * query user info by id
     * @param id user id
     * @return user info
     */
    UserInfo getById(Long id);

    /**
     * create user info
     * @param userInfo user info
     * @return complete create user info
     */
    UserInfo register(UserData userInfo);

    /**
     * query user info by ids
     * @param ids list of user id
     * @return list of user
     */
    List<UserInfo> getByIds(List<Long> ids);

    /**
     * update user info by dto
     * @param dto user info dto
     */
    void updateById(UpdateUserInfoDto dto);

    void updateByIncrOrDecrColumnDto(IncrOrDecrColumnDto dto);
}
