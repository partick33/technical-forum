package com.partick.forum.common.elasticsearch.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @author partick_peng
 */
@Data
public class EsArticle {

    private String id;

    private String articleInfoId;

    private String title;

    private String summary;

    private String url;

    private String forumName;

    private String forumCategoryName;

    private Date createTime;
}
