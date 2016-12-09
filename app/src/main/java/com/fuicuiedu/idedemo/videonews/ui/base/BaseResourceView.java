package com.fuicuiedu.idedemo.videonews.ui.base;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.fuicuiedu.idedemo.videonews.R;

/**
 * 带下拉刷新及分页加载功能的自定义视图
 * <p>
 * 列表视图 RecyclerView实现
 * <p>
 * 下拉刷新
 * <p>
 * 分页加载
 * <p>
 * 数据获取
 */
public class BaseResourceView<Model,ItemView extends BaseItemView<Model>> extends FrameLayout{

    public BaseResourceView(Context context) {
        this(context,null);
    }

    public BaseResourceView(Context context, AttributeSet attrs) {
        this(context,null,0);
    }

    public BaseResourceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    //初始化视图
    protected void initView(){
        LayoutInflater.from(getContext()).inflate(R.layout.view_base_resourse,this,true);

    }
}
