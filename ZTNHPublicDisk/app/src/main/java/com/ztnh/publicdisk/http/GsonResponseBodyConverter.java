package com.ztnh.publicdisk.http;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        //httpResult 只解析result字段
        HttpResultBean httpResult = gson.fromJson(response, HttpResultBean.class);
        //自定义异常提示语
        if (httpResult.getStatus() == 0 || httpResult.getStatus() == -1 || httpResult.getStatus() == 2) {
            throw new ApiException(httpResult.getMessage());
        }else {
            return gson.fromJson(response, type);
        }

    }
}
