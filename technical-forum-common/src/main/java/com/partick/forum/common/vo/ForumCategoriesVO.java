package com.partick.forum.common.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author partick_peng
 */
@Data
public class ForumCategoriesVO {
    @NotBlank(message = "论坛id能为空")
    private String categoryId;
    @NotNull(message = "论坛分类信息不能为空")
    private List<ForumCategoryVO> forumCategoriesVO;
}

