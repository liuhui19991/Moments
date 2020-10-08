package com.liuhui.moments.net;

/**
 * Created by liuhui on 2020/10/6.
 */

interface IShow {
    void start();

    void success(String string);

    void finish();

    void error(String message);

}
