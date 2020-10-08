package com.liuhui.moments.net;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;


/**
 * 展示等待框，数据
 * Created by liuhui on 2020/10/6.
 */

public abstract class Show implements IShow {
    private Activity mContext;
    private boolean show;
    private Dialog mDialog;
    private String title = "玩命加载中...";

    /**
     * @param isShow      是否展示等待框
     * @param showMessage 展示等待信息
     */
    public Show(Activity activity, boolean isShow, String showMessage) {
        show = isShow;
        if (!TextUtils.isEmpty(showMessage))
            title = showMessage;
    }

    @Override
    public void start() {
       //展示弹窗
    }

    @Override
    public abstract void success(String string);

    @Override
    public void finish() {//隐藏弹窗 此处应该判断activity是否销毁
    }

    @Override
    public void error(String message) {

    }
}
