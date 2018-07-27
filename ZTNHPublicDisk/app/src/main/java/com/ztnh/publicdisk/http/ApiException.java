package com.ztnh.publicdisk.http;

/**
 * Author: lijian
 * Email : 16578381@qq.com
 * Date  : 2018-01-02 10:57
 * Description: 自定义API异常
 *
 */

public class ApiException extends RuntimeException {

    public static final int REQUEST_EXCEPTION = 100;

    public static final int REQUEST_UNSUCCESS = 102;

    public static final int REQUEST_KEY_ERROR = 101;

    private String detailMessage;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "异常";
        switch (code) {
            case REQUEST_EXCEPTION:
                message = "请求异常";
                break;

            case  REQUEST_KEY_ERROR:
                message = "签名不正确";
                break;
            case  REQUEST_UNSUCCESS:
                message = "查询不到";
                break;

            default:
                message = "未知错误";

        }
        return message;
    }
}

