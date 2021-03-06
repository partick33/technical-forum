package com.partick.forum.common.config;

/**
 * 通用返回对象
 * @author partick_peng
 */
public class CommonResult<T> {

    /**
     * 业务上的成功或失败
     */
    private boolean success = true;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回泛型数据，自定义类型
     */
    private T content;

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public CommonResult() {
    }

    public CommonResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CommonResult(boolean success, String message, T content) {
        this.success = success;
        this.message = message;
        this.content = content;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResponseDto{");
        sb.append("success=").append(success);
        sb.append(", message='").append(message).append('\'');
        sb.append(", content=").append(content);
        sb.append('}');
        return sb.toString();
    }
}
