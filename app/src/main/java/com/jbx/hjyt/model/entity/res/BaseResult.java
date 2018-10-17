package com.jbx.hjyt.model.entity.res;

/**
 *
 * @author fujie
 * @date 2018/6/7
 */

public class BaseResult<T> {

    //状态
    private String status;
    //消息
    private String message;
    //请求结果的描述
    private String timestamp;
    //返回的数据内容，类型不确定，使用泛型T表示
    private T params;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }
}
