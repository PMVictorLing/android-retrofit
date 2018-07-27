package com.ztnh.publicdisk.http;

/**
 * @date 2018/3/2 15:10
 * @author lingwancai
 * @desc HttpResultBean.java{Http请求结果基类}
 */
public class HttpResultBean<T> {
    /**
     * "success": true,
     * "status": 1
     * "message":
     */

    private boolean success;
    private int status;
    private String message;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
