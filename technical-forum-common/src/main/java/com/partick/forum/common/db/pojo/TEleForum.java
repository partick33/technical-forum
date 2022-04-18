package com.partick.forum.common.db.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName t_ele_forum
 */
@TableName(value ="t_ele_forum")
@Data
public class TEleForum implements Serializable {
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
     * 论坛名字
     */
    private String forumName;

    /**
     * 论坛地址
     */
    private String forumUrl;

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