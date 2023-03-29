package com.github.veerdone.yblog.cloud.common.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Objects;

@AutoConfiguration
@EnableConfigurationProperties(ElasticSource.class)
public class ElasticAutoConfiguration implements DisposableBean {
    private ElasticsearchClient client;

    @Bean
    public ElasticsearchClient elasticsearchClient(ElasticSource elasticSource) {
        BasicCredentialsProvider credsProv = new BasicCredentialsProvider();
        credsProv.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(elasticSource.getUsername(), elasticSource.getPassword())
        );

        RestClient restClient = RestClient.builder(new HttpHost(elasticSource.getAddress(), elasticSource.getPort()))
                .setHttpClientConfigCallback(hc -> hc.setDefaultCredentialsProvider(credsProv))
                .build();
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        ElasticsearchClient client = new ElasticsearchClient(transport);
        this.client = client;

        return client;
    }

    @Override
    public void destroy() throws Exception {
        if (Objects.nonNull(this.client)) {
            client.shutdown()._transport().close();
        }
    }
}
