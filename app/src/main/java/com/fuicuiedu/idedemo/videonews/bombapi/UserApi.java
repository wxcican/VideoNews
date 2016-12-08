package com.fuicuiedu.idedemo.videonews.bombapi;

import com.fuicuiedu.idedemo.videonews.bombapi.entity.UserEntity;
import com.fuicuiedu.idedemo.videonews.bombapi.result.UserResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 用户相关的网络接口
 */

public interface UserApi {

    //用户注册
    @POST("1/users")
    Call<UserResult> register(@Body UserEntity userEntity);
}
