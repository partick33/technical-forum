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
 * @TableName t_local_message
 */
@TableName(value ="t_local_message")
@Data
public class TLocalMessage implements Serializable {
    /**
     * 逻辑主键id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 消息主键id
     */
    private String messageId;

    /**
     * 消息类型
     */
    private String messageType;

    /**
     * 消息主体
     */
    private String message;

    /**
     * 消息状态 1-已发送 12-已签收
     */
    private Integer messageCode;

    /**
     * 消息成功发送次数
     */
    private Integer messageCount;

    /**
     * 消息组
     */
    private String groupName;

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