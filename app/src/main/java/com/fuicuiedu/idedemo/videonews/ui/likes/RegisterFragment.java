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

import com.fuicuiedu.idedemo.videonews.R;
import com.fuicuiedu.idedemo.videonews.bombapi.BombClient;
import com.fuicuiedu.idedemo.videonews.bombapi.UserApi;
import com.fuicuiedu.idedemo.videonews.bombapi.entity.UserEntity;
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
 * Created by gqq on 2016/12/7.
 */

public class RegisterFragment extends DialogFragment {

    private Unbinder mUnbinder;

    @BindView(R.id.etUsername)
    EditText mEtUsername;
    @BindView(R.id.etPassword)
    EditText mEtPassword;
    @BindView(R.id.btnRegister)
    Button mBtnRegister;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //无标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_register, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnRegister)
    public void onClick() {
        final String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        //用户名密码不能为空
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtils.showShort(R.string.username_or_password_can_not_be_null);
            return;
        }
        //显示进度条
        mBtnRegister.setVisibility(View.GONE);

        //注册Api
        UserApi userApi = BombClient.getInstance().getUserApi();
        //构建用户实体类
        UserEntity userEntity = new UserEntity(username,password);
        //拿到call模型
        Call<UserResult> call = userApi.register(userEntity);
        //执行网络请求
        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                //隐藏加载圈圈
                mBtnRegister.setVisibility(View.VISIBLE);
                //注册失败
                if (!response.isSuccessful()){
                    try {
                        //拿到失败的json
                        String error = response.errorBody().string();
                        //通过gson拿到失败的结果实体类
                        ErrorResult errorResult = new Gson().fromJson(error,ErrorResult.class);
                        //提示注册失败
                        ToastUtils.showShort(errorResult.getError());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                //注册成功
                UserResult userResult = response.body();
                listener.registerSuccess(username,userResult.getObjectId());
                //提示注册成功
                ToastUtils.showShort(R.string.register_success);
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                //隐藏圈圈
                mBtnRegister.setVisibility(View.VISIBLE);
                //提示失败原因
                ToastUtils.showShort(t.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    //当注册成功会触发的方法
    public interface OnRegisterSuccessListener {
        /**
         * 当注册成功时，将来调用
         */
        void registerSuccess(String username, String objectId);
    }

    private OnRegisterSuccessListener listener;

    public void setListener(@NonNull OnRegisterSuccessListener listener) {
        this.listener = listener;
    }
}
