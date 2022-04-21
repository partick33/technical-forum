package com.partick.forum.common.mongodb.mapper;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.partick.forum.common.mongodb.pojo.ArticleInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author partick_peng
 */
@Repository
public class ArticleInfoMapper {

    @Resource
    private MongoTemplate mongoTemplate;

    public void insert(ArticleInfo articleInfo) {
        articleInfo.setCreateTime(DateUtil.offset(DateUtil.parse(DateUtil.today()), DateField.HOUR, 8));
        articleInfo.setArticleInfoId(IdUtil.fastSimpleUUID());
        mongoTemplate.save(articleInfo);
    }

    public ArticleInfo queryByTitleAndDate(String url,String forumCategoryId) {
        return mongoTemplate.findOne(new Query().addCriteria(Criteria.where("url").is(url)
                        .and("forumCategoryId").is(forumCategoryId)
                        .and("createTime").is(DateUtil.offset(DateUtil.parse(DateUtil.today()), DateField.HOUR, 8)))
                , ArticleInfo.class);
    }

    public List<ArticleInfo> queryByForumNameAndCategoryName(String forumName, String categoryName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("forumName").is(forumName)
                .and("categoryName").is(categoryName)
                .and("createTime").is(DateUtil.offset(DateUtil.parse(DateUtil.today()), DateField.HOUR, 8))
        );
        return mongoTemplate.find(query, ArticleInfo.class);
    }

    public List<ArticleInfo> queryByForumNavId(String forumCategoryId) {
        return mongoTemplate.find(new Query()
                .addCriteria(Criteria.where("forumCategoryId").is(forumCategoryId)
                        .and("createTime").is(DateUtil.offset(DateUtil.parse(DateUtil.today()), DateField.HOUR, 8))), ArticleInfo.class);
    }

    public List<ArticleInfo> queryByToday() {
        return mongoTemplate.find(new Query()
                .addCriteria(Criteria.where("createTime").is(DateUtil.offset(DateUtil.parse(DateUtil.today())
                        ,DateField.HOUR,8))),ArticleInfo.class);
    }
}
