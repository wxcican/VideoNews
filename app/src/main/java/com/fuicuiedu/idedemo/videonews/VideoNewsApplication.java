package com.fuicuiedu.idedemo.videonews;

import android.app.Application;

import com.fuicuiedu.idedemo.videonews.commons.ToastUtils;

/**
 * Created by gqq on 2016/12/7.
 */

public class VideoNewsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化吐司工具类
        ToastUtils.init(this);
    }
}
