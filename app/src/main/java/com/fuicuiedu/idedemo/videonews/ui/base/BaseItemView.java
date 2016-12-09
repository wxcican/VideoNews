package com.fuicuiedu.idedemo.videonews.ui.base;

import android.content.Context;
import android.widget.FrameLayout;

/**
 * 自定义视图的Item
 */

public abstract class BaseItemView<Model> extends FrameLayout {

    public BaseItemView(Context context) {
        super(context);
        initView();
    }

    //初始化当前视图
    protected abstract void initView();

    //将数据绑定到当前视图上
    protected abstract void bindModel(Model model);
}
