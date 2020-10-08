package com.liuhui.moments.net;

import com.liuhui.moments.utils.ThreadUtil;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求封装
 * Created by liuhui on 2020/10/6.
 */

public class Net {

    public static void go(final String url, final IShow iShow) {
        iShow.start();
        ThreadUtil.runOnThread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    final String result = response.body().string();
                    ThreadUtil.runONUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iShow.success(result);
                            iShow.finish();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    iShow.finish();
                }

            }
        });
    }
}
