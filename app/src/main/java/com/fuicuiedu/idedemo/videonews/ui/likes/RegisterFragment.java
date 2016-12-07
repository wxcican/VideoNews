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
import com.fuicuiedu.idedemo.videonews.commons.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        //用户名密码不能为空
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtils.showShort(R.string.username_or_password_can_not_be_null);
            return;
        }
        //显示进度条
        mBtnRegister.setVisibility(View.GONE);
        // TODO: 2016/12/7 执行注册的网络请求
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
