package com.partick.forum.common.mongodb.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author partick_peng
 */
@Data
@Document(collection = "articleInfo")
public class ArticleInfo implements Serializable {

    @Id
    private String id;

    @Indexed(unique = true)
    private String articleInfoId;

    private String title;

    private String summary;

    private String url;

    @Indexed
    private String categoryId;

    private String categoryName;

    private String forumName;

    private String forumCategoryId;

    private String forumCategoryName;

    @Indexed
    private Date createTime;
}
