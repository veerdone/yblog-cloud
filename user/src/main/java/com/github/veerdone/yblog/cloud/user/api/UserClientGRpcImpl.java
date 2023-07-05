package com.github.veerdone.yblog.cloud.user.api;

import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.api.IncrOrDecrColumnReq;
import com.github.veerdone.yblog.cloud.base.api.IncrOrDecrColumnResp;
import com.github.veerdone.yblog.cloud.base.api.user.IncrOrDecrColumnAndGetUserResp;
import com.github.veerdone.yblog.cloud.base.api.user.QueryUserByIdReq;
import com.github.veerdone.yblog.cloud.base.api.user.QueryUserByIdResp;
import com.github.veerdone.yblog.cloud.base.api.user.QueryUserByIdsReq;
import com.github.veerdone.yblog.cloud.base.api.user.QueryUserByIdsResp;
import com.github.veerdone.yblog.cloud.base.api.user.UserClientGrpc;
import com.github.veerdone.yblog.cloud.base.convert.UserConvert;
import com.github.veerdone.yblog.cloud.base.model.UserInfo;
import com.github.veerdone.yblog.cloud.user.service.UserInfoService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class UserClientGRpcImpl extends UserClientGrpc.UserClientImplBase {
    private final UserInfoService userInfoService;

    public UserClientGRpcImpl(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    public void queryById(QueryUserByIdReq request, StreamObserver<QueryUserByIdResp> responseObserver) {
        UserInfo userInfo = userInfoService.getById(request.getId());
        QueryUserByIdResp resp = QueryUserByIdResp.newBuilder()
                .setUserInfo(UserConvert.INSTANCE.toUserInfo(userInfo))
                .build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void queryByIds(QueryUserByIdsReq request, StreamObserver<QueryUserByIdsResp> responseObserver) {
        List<Long> idsList = request.getIdsList();
        List<UserInfo> userInfoList = userInfoService.getByIds(idsList);
        List<com.github.veerdone.yblog.cloud.base.api.user.UserInfo> respUserInfoList = new ArrayList<>(userInfoList.size());
        userInfoList.forEach(userInfo -> respUserInfoList.add(UserConvert.INSTANCE.toUserInfo(userInfo)));

        QueryUserByIdsResp resp = QueryUserByIdsResp.newBuilder()
                .addAllUserInfos(respUserInfoList)
                .build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void incrOrDecrColumn(IncrOrDecrColumnReq request, StreamObserver<IncrOrDecrColumnResp> responseObserver) {
        IncrOrDecrColumnDto dto = new IncrOrDecrColumnDto();
        dto.setNum(request.getNum());
        dto.setColumn(request.getColumn());
        dto.setItemId(request.getItemId());
        userInfoService.updateByIncrOrDecrColumnDto(dto);

        responseObserver.onNext(IncrOrDecrColumnResp.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void incrOrDecrColumnAndGet(IncrOrDecrColumnReq request, StreamObserver<IncrOrDecrColumnAndGetUserResp> responseObserver) {
        IncrOrDecrColumnDto dto = new IncrOrDecrColumnDto();
        dto.setNum(request.getNum());
        dto.setColumn(request.getColumn());
        dto.setItemId(request.getItemId());
        userInfoService.updateByIncrOrDecrColumnDto(dto);

        UserInfo userInfo = userInfoService.getById(request.getItemId());
        IncrOrDecrColumnAndGetUserResp resp = IncrOrDecrColumnAndGetUserResp.newBuilder()
                .setUserInfo(UserConvert.INSTANCE.toUserInfo(userInfo))
                .build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
