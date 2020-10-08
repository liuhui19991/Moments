package com.liuhui.moments.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.liuhui.moments.R;

/**
 * Created by liuhui on 2017/7/19.
 */
public class ImageLoaderUtil {
    public static int placeholder = R.mipmap.ic_launcher;

    public static <T> void display(ImageView view, T url) {
        displayUrl(view, url, placeholder);
    }

    public static <T> void display(ImageView view, T url, int defaultHolder) {
        displayUrl(view, url, defaultHolder);
    }

    /**
     * @param view 要展示的控件
     * @param url  要展示的地址
     */
    private static <T> void displayUrl(final ImageView view, T url, int defaultHolder) {
        if (view == null) {
            return;
        }
        Context context = view.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        Glide.with(context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存转换后的资源
                .placeholder(defaultHolder)//在图片加载完成以前显示占位符
                .crossFade()//淡入淡出动画,默认是调用的,可以传毫秒值减慢/加快,动画事件
                .into(view);
    }

    /**
     * 没有默认图片----有填充样式，低优先级加载
     *
     * @param center true 可能会完全填充，但图像可能不会完整显示使用较多 false 自适应填充
     */
    public static void loadLowPriority(ImageView imageView, String path, boolean center) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        Glide.with(context)
                .load(path)
                .priority(Priority.LOW)//设置图片加载的优先级
                .skipMemoryCache(false)//设置是不跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//缓存类型
                .crossFade()//淡入淡出动画,默认是调用的,可以传毫秒值减慢/加快,动画事件
                .centerCrop()
                .into(imageView);
    }

    /**
     * 默认的gif图片
     *
     * @param imageView 要展示的控件
     * @param resource  要展示的资源
     */
    public static <T> void displayGif(ImageView imageView, T resource) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        Glide.with(context)
                .load(resource)
                .asGif()
                .into(imageView);
    }

    public static <T> void displayRound(ImageView imageView, T url, int defaultHolder) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        Glide.with(context)
                .load(url)
                .placeholder(defaultHolder)
                .transform(new GlideRoundTransform(context))
                .centerCrop()
                .into(imageView);
    }

    public static <T> void displayRound(ImageView imageView, T url) {
        displayRound(imageView, url, placeholder);
    }

    public static void displayCircle(ImageView imageView, Object url) {
        displayCircle(imageView, url, placeholder);
    }

    public static void displayCircle(ImageView imageView, Object url, int defaultHolder) {
        if (imageView == null) {
            return;
        }
        Context context = imageView.getContext();
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        Glide.with(context)
                .load(url)
                .placeholder(defaultHolder)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .transform(new GlideCircleTransfrom(context))
                .into(imageView);
    }
}
