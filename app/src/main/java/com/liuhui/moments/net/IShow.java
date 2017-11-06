package com.liuhui.moments.net;

/**
 * Created by liuhui on 2017/11/4.
 */

interface IShow {
    void start();

    void success(String string);

    void finish();

    void error(String message);

}
