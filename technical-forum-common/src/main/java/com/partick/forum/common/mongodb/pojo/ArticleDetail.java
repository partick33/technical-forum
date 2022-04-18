package com.partick.forum.common.mongodb.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author partick_peng
 */
@Data
@Document(collection = "articleDetail")
public class ArticleDetail {

    @Indexed
    private String id;

    private String articleInfoId;

    private String title;

    private String url;

    private String text;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Indexed
    private Date createTime;
}
