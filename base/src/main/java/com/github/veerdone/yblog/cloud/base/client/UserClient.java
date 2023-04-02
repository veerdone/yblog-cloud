package com.github.veerdone.yblog.cloud.base.client;

import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;

import java.util.List;

public interface UserClient {
    UserInfo getById(Long id);

    List<UserInfo> getByIds(List<Long> ids);

    void incrOrDecrColumn(IncrOrDecrColumnDto dto);
}
