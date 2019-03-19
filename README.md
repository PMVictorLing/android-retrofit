# android-retrofit+okhttp3+rxjava+mvp网络封装
android retrofit mvp package
1.使用库：
    compile 'com.squareup.okhttp3:okhttp:3.10.0'
    //网络请求库
    compile 'com.squareup.okhttp3:logging-interceptor:3.10.0'
    //拦截器Interceptor
    compile 'com.google.code.gson:gson:2.8.2'
    //Google 提供的转换器
    compile 'com.squareup.retrofit2:retrofit:2.0.2'
    //
//    compile 'io.reactivex:rxjava:1.1.0'
    compile 'io.reactivex:rxjava:1.3.3'
    //RxJava
//    compile 'io.reactivex:rxandroid:1.1.0'
    compile 'io.reactivex:rxandroid:1.2.1'
    //RxAndroid
    compile 'com.squareup.retrofit2:converter-gson:2.0.2'
    ////转换器，请求结果转换成Model
    compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'
    
2.简单使用，具体请移步项目代码；
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
