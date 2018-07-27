package com.ztnh.publicdisk.configs;

/**
 * @date 2018/3/2 15:05
 * @author lingwancai
 * @desc Api.java{网络API接口}
 */
public class Api {

    //测试环境HOST
    public static final String API_HOST_TEST="http://testsupport.csyjmall.com/";
    //生产环境HOST
    private static final String API_HOST_PRODUCTION="";

    /**
     * 获取当前的环境下的HOST
     * @return
     */
    public static String getApiHost(){
        if (Config.IS_DEBUG)
            return API_HOST_TEST;
        return API_HOST_PRODUCTION;
    }

    public static final String API_LOGIN_URL="api/index/login";

}
