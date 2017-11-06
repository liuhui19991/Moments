package com.liuhui.moments.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.liuhui.moments.R;
import com.liuhui.moments.ui.model.CommentsModel;
import com.liuhui.moments.ui.model.ImagesModel;
import com.liuhui.moments.ui.model.MomentModel;
import com.liuhui.moments.utils.ImageLoaderUtil;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.liuhui.moments.R.id.tv_img_content;


/**
 * 朋友圈的总adapter
 * Created by liuhui on 2017/11/4.
 */
public class MomentAdapter extends BaseQuickAdapter<MomentModel, BaseViewHolder> {
    private Context mContext;

    public MomentAdapter(@LayoutRes int layoutResId, @Nullable List<MomentModel> data, Context context) {
        super(layoutResId, data);
        mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, MomentModel momentModel) {
        if (momentModel.getError() != null) {
            TextView textView = helper.getView(tv_img_content);
            textView.setText(momentModel.getError());
            textView.setTextColor(Color.RED);
            textView.setVisibility(View.VISIBLE);
            helper.getView(R.id.tv_img_name).setVisibility(View.GONE);
            helper.getView(R.id.ninePicture).setVisibility(View.GONE);
            helper.getView(R.id.rv_img_respond).setVisibility(View.GONE);
            return;
        }
        // 加载用户头像
        ImageLoaderUtil.display((ImageView) helper.getView(R.id.iv_img_head), momentModel.getSender().getAvatar());
        //用户名
        helper.setText(R.id.tv_img_name, momentModel.getSender().getNick());

        // 说说内容
        if (TextUtils.isEmpty(momentModel.getContent()))
            helper.getView(tv_img_content).setVisibility(View.GONE);
        else
            helper.setText(tv_img_content, momentModel.getContent())
                    .setVisible(R.id.tv_img_content, true)
                    .setTextColor(R.id.tv_img_content, Color.BLACK);

        //  展示说说图片
        final List<ImagesModel> picture = momentModel.getImages();
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        if (picture != null) {
            for (int i = 0; i < picture.size(); i++) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(picture.get(i).getUrl());
                info.setBigImageUrl(picture.get(i).getUrl());
                imageInfo.add(info);
            }
        }
        NineGridView nineGridView = helper.getView(R.id.ninePicture);
        nineGridView.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
        //评论
        List<CommentsModel> comment = momentModel.getComments();
        if (comment == null) comment = new ArrayList<>();
        RespondAdapter respondAdapter = new RespondAdapter(R.layout.item_response, comment);
        RecyclerView rvRespond = helper.getView(R.id.rv_img_respond);
        rvRespond.setVisibility(View.VISIBLE);
        rvRespond.setLayoutManager(new LinearLayoutManager(mContext));
        rvRespond.setAdapter(respondAdapter);
    }
}

