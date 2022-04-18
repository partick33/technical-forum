package com.partick.forum.common.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录VO
 * @author partick_peng
 */
@Data
public class LoginVO {
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;
}
