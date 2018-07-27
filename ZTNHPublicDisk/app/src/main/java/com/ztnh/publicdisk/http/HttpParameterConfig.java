package com.ztnh.publicdisk.http;

import com.google.gson.JsonObject;
import com.ztnh.publicdisk.utils.AesUtils;
import com.ztnh.publicdisk.utils.CurrencyUtils;
import com.ztnh.publicdisk.utils.LogUtils;
import com.ztnh.publicdisk.utils.MD5Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author lingwancai
 * @date 2018/3/2 15:32
 * @desc HttpParameterConfig.java{Http请求参数配置}
 */

public class HttpParameterConfig {
    private static final String TAG = "HttpParameterConfig";

    private static final String TAG_API_TOKEN = "api_token";

    /**
     * 字母排序
     */
    public static class MapComparator implements Comparator<Map.Entry<String, Object>> {
        //升序排序
        public int compare(Map.Entry<String, Object> o1,
                           Map.Entry<String, Object> o2) {
            return o1.getKey().compareTo(o2.getKey());
        }
    }

    /**
     * 参数排序，并加入api_token签名
     *
     * @param map
     */
    public static Map<String, Object> config(Map<String, Object> map) {
        if (!CurrencyUtils.isNull(map)) {
            //ASCII码排序
            List<Map.Entry<String, Object>> list = new ArrayList<>(map.entrySet());
            Collections.sort(list, new HttpParameterConfig.MapComparator());
            JsonObject json = new JsonObject();
            for (Map.Entry<String, Object> item : list) {
                String key = item.getKey();
                String value = (String) item.getValue();
                LogUtils.info(TAG + ":parameter key=" + key + ";value=" + value);
                //过滤不需要签名的参数
                if (key.equals("postscript") || key.equals("page") || key.equals("page_num")) {
                    continue;
                }
                //过滤值为int类型的参数，并转换为int类型
                if (key.equals("sex") || key.equals("order_type") || key.equals("address_id") || key.equals("province")
                        || key.equals("city") || key.equals("district") || key.equals("is_pay")
                        || key.equals("goods_number") || key.equals("is_real") || key.equals("parent_id")
                        || key.equals("country") || key.equals("is_default") || key.equals("grade")) {
                    if (CurrencyUtils.veriterNumbers(value == null ? "" : value)) {
                        if (!CurrencyUtils.isNull(value))
                            json.addProperty(key, Integer.valueOf(value));
                    } else {
                        if (!CurrencyUtils.isNull(value))
                            json.addProperty(key, value);
                    }
                } else {
//                    if (!CurrencyUtils.isNull(map.get(key)))
                        json.addProperty(key, map.get(key).toString());
                }
            }
            //AES加密
            String encryStr = AesUtils.Encrypt(json.toString());
            //MD5加密
            String result = MD5Utils.encrypt(encryStr);
            //添加签名参数到键值对
            map.put(TAG_API_TOKEN, result);
        }
        return map;
    }
}
