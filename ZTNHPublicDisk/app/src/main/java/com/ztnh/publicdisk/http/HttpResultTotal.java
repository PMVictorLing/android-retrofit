package com.ztnh.publicdisk.http;

/**
 * Author: lijian
 * Email : 16578381@qq.com
 * Date  : 2018-01-02 10:57
 * Description: 标准的数据接口返回形式（带有总条数）
 *
 */

public class HttpResultTotal<T> {

    private boolean success;
    private int status;
    private String message;
    private T data;
    private int total;

    public boolean isSuccess() {
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
