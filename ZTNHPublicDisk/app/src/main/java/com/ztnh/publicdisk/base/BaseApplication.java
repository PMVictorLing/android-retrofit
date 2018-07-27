package com.ztnh.publicdisk.base;

import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.ztnh.publicdisk.BuildConfig;
import com.ztnh.publicdisk.utils.LogUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * @author lingwancai
 * @date 2018/3/2 15:02
 * @desc BaseApplication.java{Application 基类}
 */
public class BaseApplication extends Application {

    private int versionCode;

    private String versionName;

    private static final String TAG = "BaseApplication";

    public static BaseApplication getInstanse() {
        return mInstanse;
    }

    private static BaseApplication mInstanse;

    private static List<Activity> lists = new ArrayList<>();

    public static void addActivity(Activity activity) {

        lists.add(activity);

    }

    public static void removeActivity(Activity activity) {

        lists.remove(activity);

    }

    public static void clearActivity() {
        if (lists != null) {
            for (Activity activity : lists) {
                activity.finish();
            }

            lists.clear();
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstanse = this;

        //注册记录所有activity生命周期
        registerActivityLifecycleCallbacks(callbacks);
        //初始化log日志输出
        LogUtils.init(this);

    }

    /**
     * 记录每一个Activity的生命周期
     */
    ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            LogUtils.info("onActivityCreated this Activity:" + activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            LogUtils.info("onActivityStarted this Activity:" + activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            LogUtils.info("onActivityResumed this Activity:" + activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {
            LogUtils.info("onActivityPaused this Activity:" + activity);
        }

        @Override
        public void onActivityStopped(Activity activity) {
            LogUtils.info("onActivityStopped this Activity:" + activity);
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            LogUtils.info("onActivitySaveInstanceState this Activity:" + activity);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            LogUtils.info("onActivityDestroyed this Activity:" + activity);
        }
    };

    /**
     * 获取当前应用的版本号
     *
     * @return
     */

    private void getVersion() {
        PackageManager packageManager = this.getPackageManager();
        try {
            PackageInfo info = packageManager.getPackageInfo(this.getPackageName(), 0);
            versionName = info.versionName;
            versionCode = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LogUtils.error("versionName = " + versionName + " versionCode = " + versionCode);
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getTrenchChannel() {
        String channel = "android";
//        try {
//            channel = this.getPackageManager().getApplicationInfo(getPackageName(),
//                    PackageManager.GET_META_DATA).metaData.getString("android");
//            LogUtils.e("channel = " + channel);
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }
        return channel;
    }

    /**
     * 判断是否有新版本
     *
     * @param versionCode 版本code
     * @param trench      android  ios
     * @return
     */
    public boolean updataApp(int versionCode, String trench) {
        if (getTrenchChannel().equals(trench)) {
            if (BuildConfig.VERSION_CODE < versionCode) {
                return true;
            }
        }
        return false;
    }

}
