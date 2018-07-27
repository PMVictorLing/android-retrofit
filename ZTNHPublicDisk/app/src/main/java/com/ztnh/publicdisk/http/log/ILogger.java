package com.ztnh.publicdisk.http.log;

import android.util.Log;

/**
 * Author: lijian
 * Email : 16578381@qq.com
 * Date  : 2018-01-02 10:57
 * Description: http log日志 接口
 *
 */
public interface ILogger {

    String TAG = "csyj";

    ILogger DEFAULT = new ILogger() {

        @Override
        public void log(String msg) {
            Log.i(TAG, msg);
        }
    };

    void log(String msg);

}
