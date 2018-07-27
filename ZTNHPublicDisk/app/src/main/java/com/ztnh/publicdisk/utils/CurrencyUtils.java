package com.ztnh.publicdisk.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ztnh.publicdisk.R;
import com.ztnh.publicdisk.base.BaseApplication;
import com.ztnh.publicdisk.http.ApiException;
import com.ztnh.publicdisk.widget.GlideCircleTransform;

import org.json.JSONException;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.adapter.rxjava.HttpException;

/**
 * @author lingwancai
 * @date 2018/3/2 15:31
 * @desc CurrencyUtils.java{工具类}
 */

public class CurrencyUtils {


    /**
     * 判断对象是否为空
     *
     * @param object
     * @return
     */
    public static boolean isNull(Object object) {
        if (null == object || "".equals(object) || "null".equals(object)) {
            return true;
        }
        return false;
    }

    /**
     * 验证纯数字
     *
     * @param number
     * @return
     */
    public static boolean veriterNumbers(String number) {
        if (!CurrencyUtils.isNull(number)) {
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(number);
            return m.matches();
        }
        return false;
    }


    /**
     * 设置图片
     *
     * @param url
     * @param imageView
     */
    public static void setImageviewPic(String url, ImageView imageView) {
        if (url.toUpperCase().endsWith(".GIF") || url.toUpperCase().endsWith(".gif")) {
            Glide.with(BaseApplication.getInstanse().getApplicationContext())
                    .load(url)
                    .asGif()
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        } else {
            Glide.with(BaseApplication.getInstanse().getApplicationContext())
                    .load(url)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageView);
        }
    }


    /**
     * 设置图片
     *
     * @param url
     * @param imageView
     * @param resourceId
     */
    public static void setImageviewPic(String url, ImageView imageView, int resourceId) {

        Glide.with(BaseApplication.getInstanse().getApplicationContext())
                .load(url)
                .centerCrop()
                .placeholder(resourceId)
                .error(resourceId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);

    }

    /**
     * 设置圆角图片
     *
     * @param url
     * @param imageView
     */
    public static void setImageviewCirclePic(String url, ImageView imageView) {
        Glide.with(BaseApplication.getInstanse().getApplicationContext())
                .load(url)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher).bitmapTransform(new GlideCircleTransform(BaseApplication.getInstanse().getApplicationContext()))
                .into(imageView);
    }

    /**
     * @date 2018/3/13 17:37
     * @author lingwancai
     * @desc CurrencyUtils.java{显示提示}
     */
    public static void showToastMsg(String msg) {
        LogUtils.info(msg);
        Toast.makeText(BaseApplication.getInstanse().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    /**
     * 设置RecyclerView LinearLayoutManager spacing
     */
    public static class SpacesItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    /**
     * 异常信息
     *
     * @param e
     * @return
     */
    public static String reqException(Throwable e) {
        LogUtils.info(e.getMessage());
        if (e instanceof ApiException) {
            return e.getMessage();
        } else if (e instanceof HttpException) {
            if (((HttpException) e).code() == 404) {
                return "接口不存在";
            } else if (((HttpException) e).code() == 500) {
                return "服务器的内部错误";
            } else {
                return "其它接口错误";
            }
        } else if (e instanceof JsonParseException || e instanceof JSONException) {
            return "JSON数据处理异常";
        } else {
            if (getNetWorkType() == 0) {
                return "无网络，请检查网络";
            }
            return "未知异常";
        }


    }

    /**
     * 获取网络状态
     *
     * @return int 网络状态  0无网络  1 WIFI  2MOBILE
     */
    public static int getNetWorkType() {
        ConnectivityManager manager = (ConnectivityManager) BaseApplication.getInstanse().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                return 1;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                return 2;
            }
        } else {
            return 0;
        }
        return 0;
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDCard() {
        String sdCard = "";
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdCard = Environment.getExternalStorageDirectory().toString();
        }
        return sdCard;
    }

    /**
     * 获取本地文件路径
     *
     * @return
     */
    public static String getLocalFilePath() {

        return getSDCard() + "/csyj/";
    }

    /**
     * 创建文件路径
     */
    public static void createFilePath() {
        File file = new File(getLocalFilePath());
        if (!file.exists()) {
            file.mkdir();
        }
    }

    /**
     * emoji过滤表情
     */
    public static InputFilter onInputFilterEmoji() {
        InputFilter emojiFilter = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            private int nMax;

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    showToastMsg("不支持输入特殊字符！");
                    return "";
                }

                if (source.equals(" ")){
                    return "";
                }

                //限制长度
               /* if ((dest.toString().getBytes(Charset.defaultCharset()).length <= 50 && dest.toString().length() > 10)
                        || (source.toString().getBytes(Charset.defaultCharset()).length <= 50 && source.toString().length() > 10)) {
                    nMax = 50;
                } else {
                    nMax = 50;
                }

                int keep = nMax - (dest.length() - (dend - dstart));

                if (keep <= 0) {
                    return "";
                } else if (keep >= end - start) {
                    return null; // keep original
                } else {
                    keep += start;
                    if (Character.isHighSurrogate(source.charAt(keep - 1))) {
                        --keep;
                        if (keep == start) {
                            return "";
                        }
                    }
                    return source.subSequence(start, start + keep);
                }*/
                return source;
            }
        };
        return emojiFilter;
    }


    /**
     * 显示进度条对话框
     *
     * @param context
     * @param message 对话框文本内容
     * @return 对话框对象
     */
    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //设置进度条风格，风格为长形，有刻度的
        progressDialog.setTitle("更新中");
        progressDialog.setMessage(message);
//        progressDialog.setIcon(R.drawable.android);
        progressDialog.setProgress(0);
        progressDialog.setMax(100);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 安装Apk
     *
     * @param context
     * @param file    apk 路径
     */
    public static void initApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        // 授予目录临时共享权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION| Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



}
