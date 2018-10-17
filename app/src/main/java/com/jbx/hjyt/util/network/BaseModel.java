package com.jbx.hjyt.util.network;

import com.blankj.utilcode.utils.StringUtils;

import java.io.Serializable;


public class BaseModel<T> implements Serializable {
    public String status;
    public String message;
    public T params;

    private static String SUCCEED_CODE = "10000";

    public boolean isSucceed() {
        return StringUtils.equals(status, "10000") ? true : false;
    }
    @Override
    public String toString() {
        return "BaseModel{" +
                "status='" + status + '\'' +
                ",message='" + message + '\'' +
                ",params=" + params +
                '}';
    }
}
