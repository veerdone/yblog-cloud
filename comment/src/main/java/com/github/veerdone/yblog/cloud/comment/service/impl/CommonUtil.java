package com.github.veerdone.yblog.cloud.comment.service.impl;

import com.github.veerdone.yblog.cloud.base.api.user.QueryUserByIdsReq;
import com.github.veerdone.yblog.cloud.base.api.user.QueryUserByIdsResp;
import com.github.veerdone.yblog.cloud.base.api.user.UserClientGrpc;
import com.github.veerdone.yblog.cloud.base.convert.UserConvert;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

    public static List<UserInfo> queryUserInfoByUserIds(List<Long> userIds, UserClientGrpc.UserClientBlockingStub userClientBlockingStub) {
        QueryUserByIdsResp resp = userClientBlockingStub.queryByIds(QueryUserByIdsReq.newBuilder().addAllIds(userIds).build());
        List<com.github.veerdone.yblog.cloud.base.api.user.UserInfo> respUserInfosList = resp.getUserInfosList();
        List<UserInfo> userInfoList = new ArrayList<>(respUserInfosList.size());
        respUserInfosList.forEach(userInfo -> userInfoList.add(UserConvert.INSTANCE.toUserInfo(userInfo)));

        return userInfoList;
    }
}
