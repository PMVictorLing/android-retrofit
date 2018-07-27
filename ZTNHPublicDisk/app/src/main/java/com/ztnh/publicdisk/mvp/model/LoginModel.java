package com.ztnh.publicdisk.mvp.model;

import com.ztnh.publicdisk.http.HttpRequestMethod;
import com.ztnh.publicdisk.http.RxApiManager;
import com.ztnh.publicdisk.mvp.bean.LoginBean;
import com.ztnh.publicdisk.mvp.model.Ibiz.ILoginModel;
import com.ztnh.publicdisk.mvp.view.TestV;
import com.ztnh.publicdisk.utils.CurrencyUtils;

import java.util.Map;

import rx.Subscriber;

/**
 * @date 2018/3/2 15:18
 * @author lingwancai
 * @desc LoginModel.java{TOOO}
 */
public class LoginModel implements ILoginModel {

    @Override
    public void OnTest(Map<String, Object> map, final TestV view) {
        Subscriber mSubscriber = new Subscriber<LoginBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                view.onFail(CurrencyUtils.reqException(e));
            }

            @Override
            public void onNext(LoginBean testBean) {
                view.onSuccess(testBean);
            }
        };
        HttpRequestMethod.getInstance().testRequest(mSubscriber, map);
        RxApiManager.get().add("testM",mSubscriber);
    }

    @Override
    public void cancleReq() {
        RxApiManager.get().cancel("testM");
        RxApiManager.get().cancel("requestAuthority");
    }

}
