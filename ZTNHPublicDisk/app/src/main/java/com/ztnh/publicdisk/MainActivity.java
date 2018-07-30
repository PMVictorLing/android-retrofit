package com.ztnh.publicdisk;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ztnh.publicdisk.base.BaseActivity;
import com.ztnh.publicdisk.mvp.bean.LoginBean;
import com.ztnh.publicdisk.mvp.presenter.LoginPresenter;
import com.ztnh.publicdisk.mvp.view.TestV;
import com.ztnh.publicdisk.utils.CurrencyUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author lingwancai
 * @date 2018/5/30 18:06
 * @desc MainActivity.java{主页面}
 */

public class MainActivity extends BaseActivity implements TestV {

    @BindView(R.id.titlebar_iv_back)
    ImageView titlebarIvBack;
    @BindView(R.id.titlebar_tv_title)
    TextView titlebarTvTitle;
    @BindView(R.id.titlebar_iv_other)
    ImageView titlebarIvOther;
    @BindView(R.id.tv_pay_prices)
    TextView tvPayPrices;
    @BindView(R.id.cb_wx_pay)
    CheckBox cbWxPay;
    @BindView(R.id.ll_payment_wechat)
    LinearLayout llPaymentWechat;
    @BindView(R.id.cb_zfb_pay)
    CheckBox cbZfbPay;
    @BindView(R.id.ll_payment_alipay)
    LinearLayout llPaymentAlipay;
    @BindView(R.id.ll_pay_mode)
    LinearLayout llPayMode;
    private LoginPresenter mLoginPresenter;

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View view) {

    }

    @Override
    public void doBusiness() {
        mLoginPresenter = new LoginPresenter(this);
        mLoginPresenter.OnLoginRequest();

    }

    @Override
    public void onWidgetClick(View view) {

    }

    @Override
    public void onSuccess(LoginBean bean) {
        CurrencyUtils.showToastMsg("成功 用户名="+bean.getNick_name());

    }

    @Override
    public void onFail(String msg) {
        CurrencyUtils.showToastMsg("失败 ="+msg);
    }

    @Override
    public String setUserName() {
        return "admin";
    }

    @Override
    public String setPassword() {
        return "support";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
