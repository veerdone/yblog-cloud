package com.github.veerdone.yblog.cloud.article.api;

import com.github.veerdone.yblog.cloud.article.service.ArticleInfoService;
import com.github.veerdone.yblog.cloud.article.service.ElasticService;
import com.github.veerdone.yblog.cloud.base.Dto.IncrOrDecrColumnDto;
import com.github.veerdone.yblog.cloud.base.api.IncrOrDecrColumnReq;
import com.github.veerdone.yblog.cloud.base.api.IncrOrDecrColumnResp;
import com.github.veerdone.yblog.cloud.base.api.article.ArticleClientGrpc;
import com.github.veerdone.yblog.cloud.base.api.article.IncrOrDecrColumnAndGetArticleResp;
import com.github.veerdone.yblog.cloud.base.api.article.QueryArticleByIdReq;
import com.github.veerdone.yblog.cloud.base.api.article.QueryArticleByIdResp;
import com.github.veerdone.yblog.cloud.base.api.article.QueryArticleByIdsReq;
import com.github.veerdone.yblog.cloud.base.api.article.QueryArticleByIdsResp;
import com.github.veerdone.yblog.cloud.base.api.article.UpdateArticleByIdReq;
import com.github.veerdone.yblog.cloud.base.api.article.UpdateArticleByIdResp;
import com.github.veerdone.yblog.cloud.base.api.article.UpdateStatusAndGetArticleReq;
import com.github.veerdone.yblog.cloud.base.api.article.UpdateStatusAndGetArticleResp;
import com.github.veerdone.yblog.cloud.base.api.article.UpdateStatusAndIncrOrDecrColumnReq;
import com.github.veerdone.yblog.cloud.base.api.article.UpdateStatusAndIncrOrDecrColumnResp;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.common.constant.StatusConstant;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;

@GrpcService
public class ArticleClientGrpcImpl extends ArticleClientGrpc.ArticleClientImplBase {
    private final ArticleInfoService articleInfoService;

    private final ElasticService elasticService;

    public ArticleClientGrpcImpl(ArticleInfoService articleInfoService, ElasticService elasticService) {
        this.articleInfoService = articleInfoService;
        this.elasticService = elasticService;
    }

    @Override
    public void queryArticleInfoById(QueryArticleByIdReq request, StreamObserver<QueryArticleByIdResp> responseObserver) {
        ArticleInfo articleInfo = articleInfoService.getById(request.getId());
        com.github.veerdone.yblog.cloud.base.api.article.ArticleInfo article = ArticleConvert.INSTANCE.toArticle(articleInfo);
        QueryArticleByIdResp resp = QueryArticleByIdResp.newBuilder().setArticleInfo(article).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void queryArticleInfoByIds(QueryArticleByIdsReq request, StreamObserver<QueryArticleByIdsResp> responseObserver) {
        List<Long> idList = request.getIdsList();
        List<com.github.veerdone.yblog.cloud.base.api.article.ArticleInfo> articleInfoList = new ArrayList<>(idList.size());
        idList.forEach(id -> {
            ArticleInfo articleInfo = articleInfoService.getById(id);
            articleInfoList.add(ArticleConvert.INSTANCE.toArticle(articleInfo));
        });

        QueryArticleByIdsResp resp = QueryArticleByIdsResp.newBuilder().addAllArticleInfos(articleInfoList).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void updateById(UpdateArticleByIdReq request, StreamObserver<UpdateArticleByIdResp> responseObserver) {
        com.github.veerdone.yblog.cloud.base.api.article.ArticleInfo articleInfo = request.getArticleInfo();
        ArticleInfo info = ArticleConvert.INSTANCE.toArticleInfo(articleInfo);
        articleInfoService.updateById(info);
        responseObserver.onNext(UpdateArticleByIdResp.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateStatusAndGet(UpdateStatusAndGetArticleReq request, StreamObserver<UpdateStatusAndGetArticleResp> responseObserver) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setId(request.getId());
        articleInfo.setStatus(request.getStatus());
        articleInfoService.updateById(articleInfo);

        articleInfo = articleInfoService.getById(request.getId());
        if (StatusConstant.REVIEW_THROUGH.equals(request.getStatus())) {
            elasticService.saveDocument(articleInfo);
        }

        UpdateStatusAndGetArticleResp resp = UpdateStatusAndGetArticleResp.newBuilder().setArticleInfo(ArticleConvert.INSTANCE.toArticle(articleInfo)).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }

    @Override
    public void incrOrDecrColumn(IncrOrDecrColumnReq request, StreamObserver<IncrOrDecrColumnResp> responseObserver) {
        IncrOrDecrColumnDto dto = new IncrOrDecrColumnDto();
        dto.setItemId(request.getItemId());
        dto.setColumn(request.getColumn());
        dto.setNum(request.getNum());
        articleInfoService.updateByIncrOrDecrColumnDto(dto);
        responseObserver.onNext(IncrOrDecrColumnResp.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateStatusAndIncrOrDecrColumn(UpdateStatusAndIncrOrDecrColumnReq request, StreamObserver<UpdateStatusAndIncrOrDecrColumnResp> responseObserver) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setId(request.getId());
        articleInfo.setStatus(request.getStatus());
        articleInfoService.updateById(articleInfo);

        IncrOrDecrColumnReq req = request.getIncrOrDecrColumn();
        IncrOrDecrColumnDto dto = new IncrOrDecrColumnDto();
        dto.setItemId(req.getItemId());
        dto.setColumn(req.getColumn());
        dto.setNum(req.getNum());
        articleInfoService.updateByIncrOrDecrColumnDto(dto);

        responseObserver.onNext(UpdateStatusAndIncrOrDecrColumnResp.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void incrOrDecrColumnAndGet(IncrOrDecrColumnReq request, StreamObserver<IncrOrDecrColumnAndGetArticleResp> responseObserver) {
        IncrOrDecrColumnDto dto = new IncrOrDecrColumnDto();
        dto.setItemId(request.getItemId());
        dto.setColumn(request.getColumn());
        dto.setNum(request.getNum());
        articleInfoService.updateByIncrOrDecrColumnDto(dto);

        ArticleInfo articleInfo = articleInfoService.getById(request.getItemId());
        com.github.veerdone.yblog.cloud.base.api.article.ArticleInfo article = ArticleConvert.INSTANCE.toArticle(articleInfo);
        IncrOrDecrColumnAndGetArticleResp resp = IncrOrDecrColumnAndGetArticleResp.newBuilder().setArticleInfo(article).build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
