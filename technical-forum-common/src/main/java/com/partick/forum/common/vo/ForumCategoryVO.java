package com.partick.forum.common.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author partick_peng
 */
@Data
public class ForumCategoryVO {

    private String forumNavId;
    @NotBlank(message = "论坛分类信息不能为空")
    private String forumNav;
    @NotBlank(message = "论坛分类路径不能为空")
    private String forumNavUrl;
    private String finalUrl;
}
