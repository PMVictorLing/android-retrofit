package com.ztnh.publicdisk.base;

import android.content.Context;

/**
 * @date 2018/4/8 9:44
 * @author lingwancai
 * @desc BaseDialog.java{对话框基类}
 */

public abstract class BaseDialog {

    public Context context;

    public BaseDialog(Context context, OnDialogListener listener) {
        this.listener=listener;
        this.context = context;
    }

    public abstract void initView();

    public OnDialogListener listener;

    public interface OnDialogListener {
        void OnClickListener(Object obj);
    }
}
