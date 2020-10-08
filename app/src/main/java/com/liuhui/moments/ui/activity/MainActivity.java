package com.liuhui.moments.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liuhui.moments.Constant;
import com.liuhui.moments.R;
import com.liuhui.moments.adapter.MomentAdapter;
import com.liuhui.moments.net.Net;
import com.liuhui.moments.net.Show;
import com.liuhui.moments.ui.model.MomentModel;
import com.liuhui.moments.ui.model.UserInfoModel;
import com.liuhui.moments.utils.AppCacheUtil;
import com.liuhui.moments.utils.ImageLoaderUtil;
import com.liuhui.moments.widget.FriendRefreshView;

import java.util.ArrayList;


/**
 * 展示等待框，数据
 * Created by liuhui on 2020/10/6.
 */
public class MainActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener {

    private MomentAdapter mMomentAdapter;
    private ArrayList<MomentModel> mMomentModelList;
    private int mPosition = 0;
    private FriendRefreshView.FriendRefreshRecyclerView mRecyclerView;
    private FriendRefreshView mFriendRefreshView;
    private TextView mTv_name;
    private ImageView mIv_avatar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mMomentAdapter = new MomentAdapter(R.layout.item_text_img, null, mContext);

        mFriendRefreshView = findView(R.id.moment_view);
        mRecyclerView = mFriendRefreshView.getContentView();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mMomentAdapter);
        mMomentAdapter.setOnLoadMoreListener(this, mRecyclerView);

        View headViw = LayoutInflater.from(mContext).inflate(R.layout.item_rv_head_layout, null);
        mTv_name = (TextView) headViw.findViewById(R.id.tv_name);
        mIv_avatar = (ImageView) headViw.findViewById(R.id.iv_head);
        mMomentAdapter.addHeaderView(headViw);

        mFriendRefreshView.setOnRefreshListener(new FriendRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                AppCacheUtil.getInstance(mContext).put(Constant.REQUEST_TWEETS_DATA, "");
//                mPosition = 0;
                requestData();
            }
        });
    }

    @Override
    protected void initData() {
        if (mMomentModelList != null && mMomentModelList.size() > 0) {//内存获取
            mPosition = 0;
            loadNextPage();
            return;
        }
        String user = AppCacheUtil.getInstance(mContext).getString(Constant.REQUEST_USER_DATA);
        UserInfoModel userInfoModel = JSON.parseObject(user, UserInfoModel.class);
        if (userInfoModel != null) {
            mTv_name.setText(userInfoModel.getNick());
            ImageLoaderUtil.displayRound(mIv_avatar, userInfoModel.getAvatar());
        }

        String string = AppCacheUtil.getInstance(mContext).getString(Constant.REQUEST_TWEETS_DATA);
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
        Net.go(Constant.USER, new Show(this, false, "") {
            @Override
            public void success(String string) {
                UserInfoModel userInfoModel = JSON.parseObject(string, UserInfoModel.class);
                mTv_name.setText(userInfoModel.getNick());
                ImageLoaderUtil.displayRound(mIv_avatar, userInfoModel.getAvatar());

                AppCacheUtil.getInstance(mContext).put(Constant.REQUEST_USER_DATA, string);
            }
        });

        Net.go(Constant.MOMENT, new Show(this, false, "") {

            @Override
            public void success(String string) {
                if (mMomentModelList != null) {
                    mPosition = 0;
                    mMomentModelList = null;
                    mMomentAdapter.setNewData(null);
                }
                mMomentModelList = (ArrayList<MomentModel>) JSON.parseArray(string, MomentModel.class);
                ArrayList<MomentModel> showList = new ArrayList();
                for (int i = 0; i < mMomentModelList.size(); i++) {
                    MomentModel momentModel = mMomentModelList.get(i);
                    if (momentModel.getContent() != null || momentModel.getImages() != null) {
                        showList.add(momentModel);
                    }
                }
                mMomentModelList.clear();
                mMomentModelList.addAll(showList);


                //1.请求下来的数据需要保存到本地
                AppCacheUtil.getInstance(mContext).put(Constant.REQUEST_TWEETS_DATA, JSON.toJSONString(mMomentModelList));
                ArrayList<MomentModel> currentPosition = new ArrayList<>();
                for (int i = mPosition; i < mPosition + 5; i++) {
                    currentPosition.add(mMomentModelList.get(i));
                }
                mPosition += 5;
                mMomentAdapter.addData(currentPosition);
            }

            @Override
            public void finish() {
                mFriendRefreshView.stopRefresh();
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
            if (i >= (mMomentModelList.size() - 1)) {
                if (i == (mMomentModelList.size() - 1)) {
                    currentPosition.add(mMomentModelList.get(i));
                }
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

}
