package com.liuhui.moments.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuhui.moments.R;
import com.liuhui.moments.ui.model.CommentsModel;

import java.util.List;


/**
 * 评论
 * Created by liuhui on 2017/11/4.
 */
class RespondAdapter extends BaseQuickAdapter<CommentsModel, BaseViewHolder> {

    RespondAdapter(@LayoutRes int layoutResId, @Nullable List<CommentsModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentsModel item) {
        helper.setText(R.id.response, ":" + item.getContent())
                .setText(R.id.response_name, item.getSender().getNick());
        helper.addOnClickListener(R.id.response_name);
    }

}
