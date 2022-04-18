package com.partick.forum.common.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author partick_peng
 */
@Data
public class ForumVO {
    private String forumId;
    @NotBlank(message = "论坛名称不能为空")
    private String forumName;
    @NotBlank(message = "论坛地址不能为空")
    private String forumUrl;
}
