package com.ztnh.publicdisk.base;


import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.ztnh.publicdisk.R;

import butterknife.ButterKnife;

/**
 * Author: lijian
 * Email : 16578381@qq.com
 * Date  : 2018-01-02 10:57
 * Description:  Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity
        implements IBaseView {

    /**
     * 当前Activity渲染的视图View
     */
    protected View contentView;
    /**
     * 上次点击时间
     */
    private long lastClick = 0;

    protected BaseActivity mActivity;

    public ImageView titlebar_iv_back;
    private TextView titlebar_tv_title;
    public ImageView titlebar_iv_other;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        Bundle bundle = getIntent().getExtras();
        initData(bundle);
        setBaseView(bindLayout());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);
        //设置statusbar
//        StatusBarUtil.setColor(this, Color.parseColor("#21aefe"));
        setStatusColor();
        initView(savedInstanceState, contentView);
        doBusiness();
    }

    /**
     * @date 2018/3/15 9:44
     * @author lingwancai
     * @desc BaseActivity.java{设置statusbar颜色}
     */
    private void setStatusColor() {
        Window window = getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.baseColor));
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup systemContent = (ViewGroup) findViewById(android.R.id.content);

            View statusBarView = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight());
            statusBarView.setBackgroundColor(getResources().getColor(R.color.baseColor));

            systemContent.getChildAt(0).setFitsSystemWindows(true);

            systemContent.addView(statusBarView, 0, lp);

        }
    }

    private int getStatusBarHeight() {
        Resources resources = mActivity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }


    protected void initTitle() {
        titlebar_iv_back = (ImageView) this.findViewById(R.id.titlebar_iv_back);
        titlebar_tv_title = (TextView) this.findViewById(R.id.titlebar_tv_title);
        titlebar_iv_other = (ImageView) this.findViewById(R.id.titlebar_iv_other);

    }

    protected void setTitle(String title) {
        titlebar_tv_title.setText(title);
    }

    protected void showTitleBack() {
        titlebar_iv_back.setVisibility(View.VISIBLE);
        titlebar_iv_back.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                BaseActivity.this.finish();
            }
        });
    }

    protected void showTitleOther() {
        titlebar_iv_other.setVisibility(View.VISIBLE);

    }

    protected void showTitleOther(int resId) {
        titlebar_iv_other.setVisibility(View.VISIBLE);
        titlebar_iv_other.setImageResource(resId);

    }

    protected void setBaseView(@LayoutRes int layoutId) {
        setContentView(contentView = LayoutInflater.from(this).inflate(layoutId, null));
    }

    /**
     * 判断是否快速点击
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    private boolean isFastClick() {
        long now = System.currentTimeMillis();
        if (now - lastClick >= 200) {
            lastClick = now;
            return false;
        }
        return true;
    }

    @Override
    public void onClick(final View view) {
        if (!isFastClick()) onWidgetClick(view);
    }

}