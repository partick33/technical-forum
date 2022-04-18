package com.partick.forum.common.db.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_category
 */
@TableName(value ="t_category")
@Data
public class TCategory implements Serializable {
    /**
     * 代理主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 论坛id
     */
    private String forumId;

    /**
     * 分类id
     */
    private String categoryId;

    /**
     * 分类名字
     */
    private String categoryName;

    /**
     * 分类url
     */
    private String categoryUrl;

    /**
     * 是否删除 0-否 1-是
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者
     */
    private String updateBy;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}