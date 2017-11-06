package com.liuhui.moments.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

/**
 * 基类Activity
 * Created by liuhui on 2017/11/4.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private SparseArray<View> mViews;
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViews = new SparseArray<>();
        setContentView(getLayoutId());
        mContext = this;
        initView();
        initListener();
        initData();
    }

    public <V extends View> V findView(int viewId) {
        V view = (V) mViews.get(viewId);
        if (view == null) {
            view = (V) findViewById(viewId);
            mViews.put(viewId, view);
        }
        return view;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    protected void initListener() {

    }
}
