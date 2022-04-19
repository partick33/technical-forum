package com.partick.forum.article.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
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
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

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

    /**
     * 模糊搜索标题
     *
     * @return
     */
    @Override
    public List<EsArticle> queryLikeTitle(String title) {
        RestHighLevelClient client = null;
        Set<EsArticle> esArticleSets = new HashSet<>();
        try {
            client = elasticsearchClient.getElasticsearchClient();

            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchQuery("title", title)
                            .analyzer("ik_smart").operator(Operator.OR).boost((10.0f)))
                    .should(QueryBuilders.matchQuery("summary", title)
                            .analyzer("ik_smart").operator(Operator.OR).boost((6.0f)));

            SearchSourceBuilder searchSourceBuilder =
                    new SearchSourceBuilder().query(queryBuilder).from(0).size(20);

            SearchRequest request = new SearchRequest("article").source(searchSourceBuilder);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();

            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                EsArticle esArticle = JSONObject.parseObject(sourceAsString, EsArticle.class);
                esArticleSets.add(esArticle);
            }
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
        return new ArrayList<>(esArticleSets);
    }
}
