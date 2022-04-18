package com.partick.forum.common.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author partick_peng
 */
@Data
public class CategoryVO {

    private String categoryId;
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;
    @NotBlank(message = "分类地址不能为空")
    private String categoryUrl;
}
