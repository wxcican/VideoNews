package com.fuicuiedu.idedemo.videonews.bombapi;

import com.fuicuiedu.idedemo.videonews.bombapi.entity.NewsEntity;
import com.fuicuiedu.idedemo.videonews.bombapi.result.QueryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Damon on 2016/12/25.
 */

public interface NewsApi {

    /** 获取所有视频新闻列表, 接时间新到旧排序 */
    @GET("1/classes/News?order=-createdAt")
    Call<QueryResult<NewsEntity>> getVideoNewsList(@Query("limit") int limit, @Query("skip") int skip);
}
