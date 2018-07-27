package com.ztnh.publicdisk.http;

/**
 * @date 2018/3/2 15:15
 * @author lingwancai
 * @desc HttpException.java{Http的错误信息处理}
 */
public class HttpException extends RuntimeException {

    public HttpException(int resultCode) {
        super(getHttpExceptionMessage(resultCode));
    }

    public HttpException(String resultMessage) {
        super(resultMessage);
    }

    private static String getHttpExceptionMessage(int resultCode) {
        String message="";
        switch (resultCode) {
            case 0:
                message= "000";
                break;
            case -1:
                message= "001";
                break;
            case 2:
                message= "002";
                break;
            default:
                message= "未知问题";
                break;
        }
        return message;
    }

}
