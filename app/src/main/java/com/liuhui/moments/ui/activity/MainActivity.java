package com.liuhui.moments.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liuhui.moments.Constant;
import com.liuhui.moments.R;
import com.liuhui.moments.adapter.MomentAdapter;
import com.liuhui.moments.net.Net;
import com.liuhui.moments.net.Show;
import com.liuhui.moments.ui.model.MomentModel;
import com.liuhui.moments.utils.AppCacheUtil;
import com.liuhui.moments.utils.LogUtil;

import java.util.ArrayList;


/**
 * 展示等待框，数据
 * Created by liuhui on 2017/11/4.
 */
public class MainActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MomentAdapter mMomentAdapter;
    private ArrayList<MomentModel> mMomentModelList;
    private int mPosition = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mRecyclerView = findView(R.id.recycler_view);
        mSwipeRefreshLayout = findView(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mMomentAdapter = new MomentAdapter(R.layout.item_text_img, null, mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mMomentAdapter);

        mMomentAdapter.setOnLoadMoreListener(this, mRecyclerView);
    }

    @Override
    protected void initData() {
        if (mMomentModelList != null && mMomentModelList.size() > 0) {//内存获取
            mPosition = 0;
            loadNextPage();
            return;
        }
        String string = AppCacheUtil.getInstance(mContext).getString(Constant.REQUEST_DATA);
        mMomentModelList = (ArrayList<MomentModel>) JSON.parseArray(string, MomentModel.class);
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mMomentModelList != null && mMomentModelList.size() > 0) {//磁盘获取
                    mPosition = 0;
                    loadNextPage();
                } else {//网络获取
                    requestData();
                }
            }
        }, 1000);
    }

    private void requestData() {
        Net.go(Constant.MOMENT, new Show(this, false, "") {
            @Override
            public void start() {
                if (mSwipeRefreshLayout != null && !mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            }

            @Override
            public void success(String string) {
                String result = string.replace("unknown error", "error");
                mMomentModelList = (ArrayList<MomentModel>) JSON.parseArray(result, MomentModel.class);
                //1.请求下来的数据需要保存到本地
                AppCacheUtil.getInstance(mContext).put(Constant.REQUEST_DATA, result);
                ArrayList<MomentModel> currentPosition = new ArrayList<>();
                for (int i = mPosition; i < mPosition + 5; i++) {
                    currentPosition.add(mMomentModelList.get(i));
                }
                mPosition += 5;
                mMomentAdapter.addData(currentPosition);
            }

            @Override
            public void finish() {
                if (mSwipeRefreshLayout != null)
                    mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        //上拉加载,一次取出五个
        mRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNextPage();
            }
        }, 666);
    }

    /**
     * 加载下一页
     */
    private void loadNextPage() {
        ArrayList<MomentModel> currentPosition = new ArrayList<>();
        for (int i = mPosition; i < mPosition + 5; i++) {
            //把请求下来的数据五个为一组
            if (i >= mMomentModelList.size() - 1) {
                currentPosition.add(mMomentModelList.get(i));
                mMomentAdapter.addData(currentPosition);
                mMomentAdapter.loadMoreEnd();
                return;
            }
            currentPosition.add(mMomentModelList.get(i));
        }
        mPosition += 5;
        mMomentAdapter.addData(currentPosition);
        mMomentAdapter.loadMoreComplete();
    }

    /**
     * 刷新监听。
     */
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mRecyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    AppCacheUtil.getInstance(mContext).put(Constant.REQUEST_DATA, "");
                    mMomentModelList = null;
                    mMomentAdapter.setNewData(null);
                    mPosition = 0;
                    requestData();
                }
            }, 2000);
        }
    };
}
