package com.partick.forum.article.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.partick.forum.article.service.EsArticleService;
import com.partick.forum.common.elasticsearch.config.ElasticsearchClient;
import com.partick.forum.common.elasticsearch.pojo.EsArticle;
import com.partick.forum.common.elasticsearch.pojo.EsArticleCount;
import com.partick.forum.common.elasticsearch.pojo.EsKeyWordCount;
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
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Cardinality;
import org.elasticsearch.search.aggregations.metrics.CardinalityAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
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
                bulkRequest.add(new IndexRequest("article").id(esArticle.getId()).source(source, XContentType.JSON));
            }
            BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            return bulk.hasFailures();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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

        List<EsArticle> esArticles = new ArrayList<>();
        //指定查询条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("title", title)
                        .analyzer("ik_smart").operator(Operator.OR).boost((10.0f)));
        //指定去重字段
        CollapseBuilder collapseBuilder = new CollapseBuilder("title.keyword");
        //查询去重后的结果数量
        CardinalityAggregationBuilder cardinalityAggregationBuilder = AggregationBuilders
                .cardinality("title").field("title.keyword").precisionThreshold(40000);

        SearchSourceBuilder searchSourceBuilder =
                new SearchSourceBuilder()
                        .query(queryBuilder)
                        .collapse(collapseBuilder)
                        .aggregation(cardinalityAggregationBuilder);

        SearchResponse response = defaultSearchRequest("article", searchSourceBuilder);
        SearchHit[] hits = response.getHits().getHits();

        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            EsArticle esArticle = JSONObject.parseObject(sourceAsString, EsArticle.class);
            esArticles.add(esArticle);
        }

        return esArticles;
    }

    /**
     * 查询标题中出现关键字次数
     *
     * @return
     */
    @Override
    public List<EsKeyWordCount> queryTitleKeyWordCount() {

        List<EsKeyWordCount> keyWordCount = new ArrayList<>();

        TermsAggregationBuilder titleCount = AggregationBuilders
                .terms("title_count")
                .field("title.keyword")
                .size(10);

        SearchSourceBuilder searchSourceBuilder =
                new SearchSourceBuilder().aggregation(titleCount);

        SearchResponse response = defaultSearchRequest("article", searchSourceBuilder);
        Terms terms = response.getAggregations().get("title_count");
        for (int i = 0; i < terms.getBuckets().size(); i++) {
            Terms.Bucket bucket = terms.getBuckets().get(i);
            EsKeyWordCount esKeyWordCount = new EsKeyWordCount();
            esKeyWordCount.setKeyWord(bucket.getKeyAsString());
            esKeyWordCount.setDocCount(bucket.getDocCount());
            keyWordCount.add(esKeyWordCount);
        }
        return keyWordCount;
    }

    /**
     * 分组统计每个论坛的文章总数
     *
     * @return
     */
    @Override
    public List<EsArticleCount> queryEsArticleCountByForum() {
        List<EsArticleCount> esArticleCounts = new ArrayList<>();
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("terms").field("forumName.keyword");
        termsAggregationBuilder.subAggregation(
                AggregationBuilders.cardinality("articleGroupByForumName").field("title.keyword")
        );
        SearchSourceBuilder sourceBuilder = SearchSourceBuilder.searchSource()
                .aggregation(termsAggregationBuilder);
        SearchResponse response = defaultSearchRequest("article", sourceBuilder);
        Terms terms = response.getAggregations().get("terms");
        for (int i = 0; i < terms.getBuckets().size(); i++) {
            Terms.Bucket bucket = terms.getBuckets().get(i);
            Cardinality groupByForumName = bucket.getAggregations().get("articleGroupByForumName");
            EsArticleCount esArticleCount = new EsArticleCount();
            esArticleCount.setName(bucket.getKeyAsString());
            esArticleCount.setValue(groupByForumName.getValue());
            esArticleCounts.add(esArticleCount);
        }
        return esArticleCounts;
    }

    private SearchResponse defaultSearchRequest(String index, SearchSourceBuilder sourceBuilder) {
        RestHighLevelClient client = null;
        SearchResponse response = null;
        try {
            client = elasticsearchClient.getElasticsearchClient();
            SearchRequest request = new SearchRequest(index).source(sourceBuilder);
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

}