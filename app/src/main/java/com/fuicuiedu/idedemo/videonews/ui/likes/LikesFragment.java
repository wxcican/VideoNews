package com.fuicuiedu.idedemo.videonews.ui.likes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fuicuiedu.idedemo.videonews.R;
import com.fuicuiedu.idedemo.videonews.ui.UserManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的收藏页面
 * 上部：个人信息，登陆注册
 * 下部：收藏列表
 */

public class LikesFragment extends Fragment implements
        LoginFragment.OnLoginSuccessListener,
        RegisterFragment.OnRegisterSuccessListener{

    @BindView(R.id.tvUsername)
    TextView mTvUsername;
    @BindView(R.id.btnRegister)
    Button mBtnRegister;
    @BindView(R.id.divider)
    View mDivider;
    @BindView(R.id.btnLogin)
    Button mBtnLogin;
    @BindView(R.id.btnLogout)
    Button mBtnLogout;
    @BindView(R.id.likesListView)
    LikesListView likesListView;//收藏列表
    private View view;

    private LoginFragment mLoginFragment;
    private RegisterFragment mRegisterFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_likes, container, false);
            ButterKnife.bind(this, view);
            //判断用户登录状态，更新UI
            UserManager userManager = UserManager.getInstance();
            if (!userManager.isOffline()){
                userOnLine(userManager.getUsername(),userManager.getObjectId());
            }
            //将FragmentManager传递给收藏列表
            likesListView.setFragmentManager(getFragmentManager());
        }
        return view;
    }

    @OnClick({R.id.btnRegister, R.id.btnLogin, R.id.btnLogout})
    public void onClick(View view) {
        switch (view.getId()) {
            //显示注册的fragment
            case R.id.btnRegister:
                if (mRegisterFragment == null){
                    mRegisterFragment = new RegisterFragment();
                    mRegisterFragment.setListener(this);
                }
                mRegisterFragment.show(getChildFragmentManager(),"Register Dialog");
                break;
            //显示登录的fragment
            case R.id.btnLogin:
                if (mLoginFragment == null){
                    mLoginFragment = new LoginFragment();
                    mLoginFragment.setListener(this);
                }
                mLoginFragment.show(getChildFragmentManager(),"Login Dialog");
                break;
            //登出
            case R.id.btnLogout:
                //用户下线
                userOffline();
                break;
        }
    }

    //登录成功
    @Override
    public void loginSuccess(String username, String objectId) {
        mLoginFragment.dismiss();
        //用户上线
        userOnLine(username, objectId);
    }

    @Override
    public void registerSuccess(String username, String objectId) {
        mRegisterFragment.dismiss();
        //用户上线
        userOnLine(username, objectId);
    }

    //用户下线
    private void userOffline(){
        //清除用户信息
        UserManager.getInstance().clear();
        //更新UI
        mBtnLogout.setVisibility(View.INVISIBLE);
        mBtnLogin.setVisibility(View.VISIBLE);
        mBtnRegister.setVisibility(View.VISIBLE);
        mDivider.setVisibility(View.VISIBLE);
        mTvUsername.setText(R.string.tourist);
        //清空收藏列表(adapter上的内容clear了)
        likesListView.clear();
    }
    
    //用户上线
    private void userOnLine(String username, String objectId){
        //存储用户信息
        UserManager.getInstance().setUsername(username);
        UserManager.getInstance().setObjectId(objectId);
        //更新UI
        mBtnLogout.setVisibility(View.VISIBLE);
        mBtnLogin.setVisibility(View.INVISIBLE);
        mBtnRegister.setVisibility(View.INVISIBLE);
        mDivider.setVisibility(View.INVISIBLE);
        mTvUsername.setText(username);
        //刷新收藏列表
        likesListView.autoRefresh();
    }
}
