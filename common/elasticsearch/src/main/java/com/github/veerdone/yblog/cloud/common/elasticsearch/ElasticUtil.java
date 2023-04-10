package com.github.veerdone.yblog.cloud.common.elasticsearch;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.HitsMetadata;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ElasticUtil {
    private static final ThreadLocal<Number> LOCAL_TOTAL = new ThreadLocal<>();

    public static void setLocalTotal(Number number) {
        LOCAL_TOTAL.set(number);
    }

    public static Number getLocalTotal() {
        return LOCAL_TOTAL.get();
    }

    public static void cleanThreadLocal() {
        LOCAL_TOTAL.remove();
    }

    public static <T> List<T> searchResp(SearchResponse<T> searchResponse) {
        HitsMetadata<T> hits = searchResponse.hits();
        if (Objects.nonNull(hits)) {
            List<Hit<T>> hitList = hits.hits();
            if (Objects.nonNull(hitList)) {
                return hitList.stream().map(hit -> {
                    T source = hit.source();
                    if (Objects.nonNull(source)) {
                        Map<String, List<String>> highlight = hit.highlight();
                        if (Objects.nonNull(highlight)) {
                            highlight.keySet().forEach(key -> {
                                List<String> stringList = highlight.get(key);
                                if (CollectionUtil.isNotEmpty(stringList)) {
                                    ReflectUtil.setFieldValue(source, key, stringList.get(0));
                                }
                            });
                        }
                    }

                    return source;
                }).collect(Collectors.toList());
            }
        }

        return Collections.emptyList();
    }
}
