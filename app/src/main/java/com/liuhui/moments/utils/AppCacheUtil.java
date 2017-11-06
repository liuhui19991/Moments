package com.liuhui.moments.utils;

import android.content.Context;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 缓存设置
 * Created by liuhui on 2015/7/27.
 */
public class AppCacheUtil {

    private static final String DEFAULT_CACHE_NAME = "appCache";

    private File mCacheFile;
    private static Map<String, AppCacheUtil> mCacheUtilsMap = new HashMap<>();

    private AppCacheUtil(File cacheFile) {
        mCacheFile = cacheFile;
        if (!mCacheFile.exists()) {
            mCacheFile.mkdirs();
        }
    }

    public static AppCacheUtil getInstance(Context ctx) {
        return getInstance(new File(ctx.getCacheDir(), DEFAULT_CACHE_NAME));
    }


    public static AppCacheUtil getInstance(File file) {
        AppCacheUtil appCacheUtils = mCacheUtilsMap.get(file.getAbsolutePath());
        if (appCacheUtils == null) {
            appCacheUtils = new AppCacheUtil(file);
            mCacheUtilsMap.put(file.getAbsolutePath(), appCacheUtils);
        }
        return appCacheUtils;
    }

    /**
     * 保存 String数据 到 缓存中
     *
     * @param key   保存的key
     * @param value 保存的String数据
     */
    public void put(String key, String value) {

        if (TextUtils.isEmpty(key)) {
            return;
        }

        if (TextUtils.isEmpty(value)) {
            value = "";
        }
        File file = newFile(key);
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(file), 1024);
            out.write(value);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 读取 String数据
     *
     * @return String 数据
     */
    public String getString(String key) {
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        File file = newFile(key);
        if (!file.exists()) {
            return null;
        }
        BufferedReader in = null;
        String readString = "";
        try {
            in = new BufferedReader(new FileReader(file));
            String currentLine;
            while ((currentLine = in.readLine()) != null) {
                readString += currentLine;
            }
            return readString;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return readString;
    }

    private File newFile(String key) {
        return new File(mCacheFile, MD5Util.encode(key));
    }
}

