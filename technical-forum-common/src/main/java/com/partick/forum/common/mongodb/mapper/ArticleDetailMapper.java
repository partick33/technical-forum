package com.partick.forum.common.mongodb.mapper;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.partick.forum.common.mongodb.pojo.ArticleDetail;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author partick_peng
 */
@Repository
public class ArticleDetailMapper {
    @Resource
    private MongoTemplate mongoTemplate;

    public void insert(ArticleDetail articleDetail) {
        articleDetail.setCreateTime(DateUtil.offset(DateUtil.parse(DateUtil.today()), DateField.HOUR, 8));
        mongoTemplate.save(articleDetail);
    }

    public ArticleDetail queryByArticleIdAndDate(String articleId) {
        return mongoTemplate.findOne(new Query().addCriteria(Criteria.where("articleInfoId").is(articleId)
                .and("createTime").is(DateUtil.offset(DateUtil.parse(DateUtil.today()), DateField.HOUR, 8)))
                , ArticleDetail.class);
    }

    public ArticleDetail queryByArticleInfoId(String articleInfoId) {
        return mongoTemplate.findOne(new Query().addCriteria(Criteria.where("articleInfoId").is(articleInfoId)),ArticleDetail.class);
    }
}
