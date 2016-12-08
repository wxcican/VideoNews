package com.fuicuiedu.idedemo.videonews.bombapi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/12/8 0008.
 */

public class BombClient {
    private static BombClient bombClient;

    public static BombClient getInstance(){
        if (bombClient == null){
            bombClient = new BombClient();
        }
        return bombClient;
    }

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    private UserApi userApi;

    private BombClient(){
        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //构建OkHttp
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new BombInterceptor())//添加Bomb需要的请求头的拦截器
                .addInterceptor(httpLoggingInterceptor)//日志拦截器
                .build();

        //构建Retrofit
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                // bomb服务器baseurl
                .baseUrl("https://api.bmob.cn/")
                // Gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //拿到UserApi
    public UserApi getUserApi(){
        if (userApi == null){
            userApi = retrofit.create(UserApi.class);
        }
        return userApi;
    }



}
