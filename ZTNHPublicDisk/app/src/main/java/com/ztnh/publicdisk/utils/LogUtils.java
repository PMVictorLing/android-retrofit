package com.ztnh.publicdisk.utils;

import android.content.Context;
import android.util.Log;


import com.ztnh.publicdisk.R;
import com.ztnh.publicdisk.configs.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @date 2018/3/2 15:00
 * @author lingwancai
 * @desc LogUtils.java{log日志输出}
 */
public class LogUtils {

    private static String TAG = "LogUtils";

    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static void init(Context context) {
        TAG = context.getString(R.string.app_name);
    }

    public static void info(String msg) {
        if (Config.IS_SHOW_LOG)
            Log.i(TAG, msg);
    }

    public static void error(String msg) {
        if (Config.IS_SHOW_LOG)
            Log.e(TAG, msg);
    }


    public static void printLine(String tag, boolean isTop) {
        if (isTop) {
            Log.d(tag, "╔═══════════════════════════════════════════════════════════════════════════════════════");
        } else {
            Log.d(tag, "╚═══════════════════════════════════════════════════════════════════════════════════════");
        }
    }
    public static void printJson(String tag, String msg, String headString) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(4);//最重要的方法，就一行，返回格式化的json字符串，其中的数字4是缩进字符数
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(4);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        printLine(tag, true);
        message = headString + LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            Log.d(tag, "║ " + line);
        }
        printLine(tag, false);
    }


}
