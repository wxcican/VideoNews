package com.fuicuiedu.idedemo.videonews.ui.likes;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.fuicuiedu.idedemo.videonews.R;
import com.fuicuiedu.idedemo.videonews.bombapi.BombClient;
import com.fuicuiedu.idedemo.videonews.commons.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by gqq on 2016/12/7.
 */

public class LoginFragment extends DialogFragment {

    private Unbinder mUnbinder;

    @BindView(R.id.etUsername)
    EditText mEtUsername;
    @BindView(R.id.etPassword)
    EditText mEtPassword;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //无标题栏
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_login, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnLogin)
    public void onClick() {
        String username = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        //用户名密码能不为空
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            ToastUtils.showShort(R.string.username_or_password_can_not_be_null);
            return;
        }
        //显示进度条
        mBtnLogin.setVisibility(View.GONE);
        // TODO: 2016/12/7 执行登录的网络请求
        Call call = BombClient.getInstance().register(username, password);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 1;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = 2;
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mBtnLogin.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    listener.loginSuccess("a","b");
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }


    //当登录成功之后触发的方法
    public interface OnLoginSuccessListener {
        /**
         * 当登录成功时，将来调用
         */
        void loginSuccess(String username, String objectId);
    }

    private OnLoginSuccessListener listener;

    public void setListener(@NonNull OnLoginSuccessListener listener) {
        this.listener = listener;
    }
}
