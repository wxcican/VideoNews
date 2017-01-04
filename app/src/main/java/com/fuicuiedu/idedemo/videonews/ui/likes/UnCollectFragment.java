package com.fuicuiedu.idedemo.videonews.ui.likes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.fuicuiedu.idedemo.videonews.R;
import com.fuicuiedu.idedemo.videonews.bombapi.BombClient;
import com.fuicuiedu.idedemo.videonews.bombapi.UserApi;
import com.fuicuiedu.idedemo.videonews.bombapi.result.CollectResult;
import com.fuicuiedu.idedemo.videonews.bombapi.result.ErrorResult;
import com.fuicuiedu.idedemo.videonews.bombapi.result.UserResult;
import com.fuicuiedu.idedemo.videonews.commons.ToastUtils;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 取消收藏的对话框
 */

public class UnCollectFragment extends DialogFragment {

    @BindView(R.id.dialog_un_collect_btn)View view;//显示确定和取消按钮的布局
    @BindView(R.id.dialog_un_collect_prb)ProgressBar progressBar;//进度条
    private Unbinder mUnbinder;
    private String newsId;
    private String userId;

    public UnCollectFragment(String newsId,String userId) {
        this.newsId = newsId;
        this.userId = userId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //无标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_un_collect, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.dialog_un_collect_ok,R.id.dialog_un_collect_no})
    public void onClike(Button button){
        switch (button.getId()){
            //确认
            case R.id.dialog_un_collect_ok:
                view.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                //点击确认执行取消收藏操作
                Call<CollectResult> call = BombClient.getInstance().getNewsApi().unCollectNews(
                        newsId,
                        userId);
                call.enqueue(new Callback<CollectResult>() {
                    @Override
                    public void onResponse(Call<CollectResult> call, Response<CollectResult> response) {
                        dismiss();
                        CollectResult collectResult = response.body();
                        if (collectResult.isSuccess()) {
                            ToastUtils.showShort("取消收藏成功");
                            listener.UnCollectSuccess();
                        } else {
                            ToastUtils.showShort("取消收藏失败：" + collectResult.getError());
                        }
                    }

                    @Override
                    public void onFailure(Call<CollectResult> call, Throwable t) {
                        dismiss();
                        ToastUtils.showShort(t.getMessage());
                    }
                });
                break;
            //取消
            case R.id.dialog_un_collect_no:
                dismiss();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }



    //点击确认或取消触发的方法
    public interface OnUnCollectSuccessListener {
        void UnCollectSuccess();
    }

    private OnUnCollectSuccessListener listener;

    public void setListener(@NonNull OnUnCollectSuccessListener listener) {
        this.listener = listener;
    }
}
