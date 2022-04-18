package com.partick.forum.article.service.impl;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.partick.forum.article.service.EsArticleService;
import com.partick.forum.common.elasticsearch.config.ElasticsearchClient;
import com.partick.forum.common.elasticsearch.pojo.EsArticle;
import com.partick.forum.common.mongodb.mapper.ArticleInfoMapper;
import com.partick.forum.common.mongodb.pojo.ArticleInfo;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author partick_peng
 */
@Service
public class EsArticleServiceImpl implements EsArticleService {

    @Resource
    private ArticleInfoMapper articleInfoMapper;

    @Resource
    private ElasticsearchClient elasticsearchClient;

    /**
     * 批量推送文章概要信息到Es
     *
     * @return
     */
    @Override
    public Boolean putBulkEsArticle() {
        RestHighLevelClient client = null;
        try {
            client = elasticsearchClient.getElasticsearchClient();
            BulkRequest bulkRequest = new BulkRequest();
            bulkRequest.timeout("2s");
            List<ArticleInfo> articleInfos = articleInfoMapper.queryByToday();
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            for (ArticleInfo articleInfo : articleInfos) {
                EsArticle esArticle = new EsArticle();
                BeanUtils.copyProperties(articleInfo, esArticle);
                esArticle.setCreateTime(DateUtil.date());
                String source = mapper.writeValueAsString(esArticle);
                bulkRequest.add( new IndexRequest("article").id(esArticle.getId()).source(source, XContentType.JSON));
            }
            BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            return bulk.hasFailures();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
