package com.partick.forum.common.config;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author partick_peng
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionConfig {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public CommonResult handleGlobalException(HttpServletResponse response,Exception e) {
        log.error("{}时间出现异常：{}",DateUtil.date(),e.getMessage());
        CommonResult<Object> result = new CommonResult<>();
        result.setContent(e.getMessage());
        result.setMessage("服务器异常");
        result.setSuccess(false);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.setContentType("application/json;charset=UTF-8");
        return result;
    }
}
