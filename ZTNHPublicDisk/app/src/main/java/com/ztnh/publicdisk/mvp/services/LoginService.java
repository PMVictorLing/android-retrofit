package com.ztnh.publicdisk.mvp.services;

import com.ztnh.publicdisk.configs.Api;
import com.ztnh.publicdisk.http.HttpResultBean;
import com.ztnh.publicdisk.mvp.bean.LoginBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * @author lingwancai
 * @date 2018/3/2 15:37
 * @desc LoginService.java{TOOO}
 */
public interface LoginService {
    /**
     * 请求用例
     *
     * @param options
     * @return
     */
    @FormUrlEncoded
    @POST(Api.API_LOGIN_URL)
    Observable<HttpResultBean<LoginBean>> request
    (
            @FieldMap Map<String, Object> options
    );

}
