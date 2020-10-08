package com.liuhui.moments;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.liuhui.moments.utils.ImageLoaderUtil;
import com.lzy.ninegrid.NineGridView;


/**
 * Created by liuhui on 2020/10/6.
 */

public class BaseApplication extends Application {
    private static BaseApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        NineGridView.setImageLoader(new GlideImageLoader());
    }

    public static BaseApplication getInstance() {
        return mContext;
    }

    /**
     * Picasso 加载
     */
    private class GlideImageLoader implements NineGridView.ImageLoader {

        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            ImageLoaderUtil.display(imageView,url);
        }

        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
}
