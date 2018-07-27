package com.ztnh.publicdisk.http;


import android.util.Log;

import com.ztnh.publicdisk.configs.Api;
import com.ztnh.publicdisk.http.log.HttpLogInterceptor;
import com.ztnh.publicdisk.http.log.ILogger;
import com.ztnh.publicdisk.mvp.bean.LoginBean;
import com.ztnh.publicdisk.mvp.services.LoginService;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @date 2018/3/2 15:16
 * @author lingwancai
 * @desc HttpRequestMethod.java{Http请求方法集合}
 */
public class HttpRequestMethod extends BaseMethod {


    private Retrofit retrofit;

    private OkHttpClient getOkHttpClient() {

        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder().connectTimeout(180, TimeUnit.SECONDS).
                readTimeout(180, TimeUnit.SECONDS).
                writeTimeout(180, TimeUnit.SECONDS);
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(new HttpLogInterceptor(new ILogger() {
            @Override
            public void log(String msg) {
                Log.d("NetWork", msg);
            }


        }));
        return httpClientBuilder.build();

    }

    //构造方法私有
    private HttpRequestMethod() {
        retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())//使用自己创建的OkHttp
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Api.getApiHost())
                .build();
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpRequestMethod INSTANCE = new HttpRequestMethod();
    }

    //获取单例
    public static HttpRequestMethod getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 订阅http请求
     *
     * @param o
     * @param s
     * @param <T>
     */
    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Func1<HttpResultBean<T>, T> {

        @Override
        public T call(HttpResultBean<T> httpResult) {
            //分发错误状态
            if (httpResult.getStatus() == 0) {
                throw new ApiException(100);
            } else if (httpResult.getStatus() == 2) {
                throw new ApiException(102);
            } else {
                return httpResult.getData();
            }
        }
    }


    /**
     * 请求用例
     * @param subscriber
     * @param map
     */
    public void testRequest(Subscriber<LoginBean> subscriber, Map<String, Object> map) {
        super.configMap(map);
        Observable observable = retrofit.create(LoginService.class).request(map).map(new HttpResultFunc<LoginBean>());
        toSubscribe(observable, subscriber);
    }





}
