package com.ztnh.publicdisk.mvp.presenter;



import com.ztnh.publicdisk.mvp.model.LoginModel;
import com.ztnh.publicdisk.mvp.view.TestV;

import java.util.HashMap;

/**
 * @date 2018/3/2 15:22
 * @author lingwancai
 * @desc LoginPresenter.java{TOOO}
 */

public class LoginPresenter {
    private TestV view;
    private LoginModel model;

    public LoginPresenter(TestV view) {
        this.view = view;
        model = new LoginModel();
    }

    public void OnLoginRequest() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("user_name", view.setUserName());
        map.put("password", view.setPassword());
        model.OnTest(map,view);
    }

    public void cancelReq(){
        model.cancleReq();
    }

}
