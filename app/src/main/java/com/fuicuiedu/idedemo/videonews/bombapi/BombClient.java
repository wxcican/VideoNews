package com.fuicuiedu.idedemo.videonews.bombapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    // 用于请求新的服务器地址
    private Retrofit retrofit_cloud;
    private UserApi userApi;
    private NewsApi newsApi;
    // 用于构建新的服务器请求
    private NewsApi newsApi_cloud;

    private BombClient(){
        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //构建OkHttp
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new BombInterceptor())//添加Bomb需要的请求头的拦截器
                .addInterceptor(httpLoggingInterceptor)//日志拦截器
                .build();

        // 让Gson能将bomb返回的时间戳自动转为date对象
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        //构建Retrofit
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                // bomb服务器baseurl
                .baseUrl("https://api.bmob.cn/")
                // Gson转换器
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        //构建retrofit_cloud
        retrofit_cloud = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://cloud.bmob.cn/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    //拿到UserApi
    public UserApi getUserApi(){
        if (userApi == null){
            userApi = retrofit.create(UserApi.class);
        }
        return userApi;
    }

    public NewsApi getNewsApi(){
        if (newsApi == null){
            newsApi = retrofit.create(NewsApi.class);
        }
        return newsApi;
    }

    public NewsApi getNewsApi_cloud(){
        if (newsApi_cloud == null){
            newsApi_cloud = retrofit_cloud.create(NewsApi.class);
        }
        return newsApi_cloud;
    }



}
