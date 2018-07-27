package com.ztnh.publicdisk.http;

import java.util.Map;

/**
 * @date 2018/3/2 15:11
 * @author lingwancai
 * @desc BaseMethod.java{初始化参数排序，并签名}
 */
public abstract class BaseMethod {

    public void configMap(Map<String,Object> map){
        HttpParameterConfig.config(map);
    }

}
