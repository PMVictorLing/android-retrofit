package com.ztnh.publicdisk.mvp.view;


import com.ztnh.publicdisk.mvp.bean.LoginBean;

/**
 * @author liusir_xy
 * @date 2017/9/28 10:42
 * @desc {TOOO}
 */
public interface TestV {

    void onSuccess(LoginBean bean);

    void onFail(String msg);

    String setUserName();

    String setPassword();
}
