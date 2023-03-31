package com.github.veerdone.yblog.cloud.article.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.github.veerdone.yblog.cloud.article.service.ElasticService;
import com.github.veerdone.yblog.cloud.base.Dto.ArticleDocumentDto;
import com.github.veerdone.yblog.cloud.base.Dto.ArticleSearchDto;
import com.github.veerdone.yblog.cloud.base.convert.ArticleConvert;
import com.github.veerdone.yblog.cloud.base.model.ArticleInfo;
import com.github.veerdone.yblog.cloud.common.constant.ElasticConstant;
import com.github.veerdone.yblog.cloud.common.elasticsearch.ElasticUtil;
import com.github.veerdone.yblog.cloud.common.exception.ServiceException;
import com.github.veerdone.yblog.cloud.common.exception.ServiceExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ElasticServiceImpl implements ElasticService {
    private static final Logger log = LoggerFactory.getLogger(ElasticServiceImpl.class);

    private final ElasticsearchClient elasticsearchClient;

    public ElasticServiceImpl(ElasticsearchClient elasticsearchClient) {
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public void saveDocument(ArticleInfo articleInfo) {
        ArticleDocumentDto dto = ArticleConvert.INSTANCE.toDocumentDto(articleInfo);

        try {
            elasticsearchClient.index(i -> i.index(ElasticConstant.ARTICLE_INDEX)
                    .id(String.valueOf(dto.getId()))
                    .document(dto)
            );
        } catch (IOException e) {
            log.warn("save article document failed, articleId={}, reason: ", articleInfo.getId(), e);
            throw new ServiceException(ServiceExceptionEnum.INNER_SERVICE_ERROR);
        }
    }

    @Override
    public List<ArticleDocumentDto> search(ArticleSearchDto dto) {
        try {
            SearchResponse<ArticleDocumentDto> response = elasticsearchClient.search(
                    s -> s.index(ElasticConstant.ARTICLE_INDEX)
                            .query(q -> q.bool(b -> b.should(
                                    Query.of(f -> f.match(m -> m.field("title").query(dto.getKeyword()))),
                                    Query.of(f -> f.match(m -> m.field("description").query(dto.getKeyword())))
                            )))
                            .highlight(h -> h.preTags("<span color='red'>")
                                    .postTags("</span>")
                                    .fields("title", f -> f)
                                    .fields("description", f -> f)
                            )
                            .from(dto.getFrom())
                            .size(dto.getSize()),
                    ArticleDocumentDto.class);

            ElasticUtil.setLocalTotal(response.shards().total());

            return ElasticUtil.searchResp(response);
        } catch (IOException e) {
            log.warn("search article fail, keyword={}", dto.getKeyword(), e);
            throw new ServiceException(ServiceExceptionEnum.INNER_SERVICE_ERROR);
        }
    }
}
