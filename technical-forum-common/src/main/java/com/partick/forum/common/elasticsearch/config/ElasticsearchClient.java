package com.partick.forum.common.elasticsearch.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author partick_peng
 */
@Component
public class ElasticsearchClient {


    @Bean(name = "getElasticsearchClient")
    public RestHighLevelClient getElasticsearchClient() {
        HttpHost[] httpHosts = new HttpHost[4];
        for (int i = 109,j=0; i < 113; i++,j++) {
            httpHosts[j] = new HttpHost("192.168.116."+ i, 9200);
        }
        return new RestHighLevelClient(RestClient.builder(httpHosts));
    }
}
