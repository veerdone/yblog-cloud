package com.github.veerdone.yblog.cloud.user.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.github.veerdone.yblog.cloud.base.Dto.user.UpdateUserInfoDto;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;

import java.util.List;

public interface UserInfoService {
    /**
     * query user info by id
     * @param id user id
     * @return user info
     */
    UserInfo getUserInfoById(Long id);

    /**
     * create user info
     * @param userInfo user info
     * @return complete create user info
     */
    UserInfo create(UserInfo userInfo);

    /**
     * query user info by ids
     * @param ids list of user id
     * @return list of user
     */
    List<UserInfo> getUserInfoByIds(List<Long> ids);

    /**
     * update user info by dto
     * @param dto user info dto
     */
    void updateUserInfoById(UpdateUserInfoDto dto);

    /**
     * update user info by mybatis plus wrapper
     * @param wrapper update wrapper
     */
    void updateByWrapper(Wrapper<UserInfo> wrapper);
}
