package com.fuicuiedu.idedemo.videonews.bombapi;

import com.fuicuiedu.idedemo.videonews.bombapi.entity.CommentsEntity;
import com.fuicuiedu.idedemo.videonews.bombapi.entity.NewsEntity;
import com.fuicuiedu.idedemo.videonews.bombapi.entity.PublishEntity;
import com.fuicuiedu.idedemo.videonews.bombapi.other.InQuery;
import com.fuicuiedu.idedemo.videonews.bombapi.result.CollectResult;
import com.fuicuiedu.idedemo.videonews.bombapi.result.CreateResult;
import com.fuicuiedu.idedemo.videonews.bombapi.result.QueryResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Damon on 2016/12/25.
 */

public interface NewsApi {

    /** 获取所有视频新闻列表, 接时间新到旧排序 */
    @GET("1/classes/News?order=-createdAt")
    Call<QueryResult<NewsEntity>> getVideoNewsList(@Query("limit") int limit, @Query("skip") int skip);

    /** 获取评论*/
    @GET("1/classes/Comments?include=author&order=-createdAt")
    Call<QueryResult<CommentsEntity>> getComments(
            @Query("limit") int limit,
            @Query("skip") int skip,
            @Query("where") InQuery where);

//  收藏新闻
    @GET("http://cloud.bmob.cn/bef74a37a08d3205/changeLike?action=like")
    Call<CollectResult> collectNews(@Query("newsId") String newsId, @Query("userId") String userId);
//  取消收藏新闻
    @GET("http://cloud.bmob.cn/bef74a37a08d3205/changeLike?action=dislike")
    Call<CollectResult> unCollectNews(@Query("newsId") String newsId,@Query("userId") String userId);

    /** 发表评论*/
    @POST("1/classes/Comments")
    Call<CreateResult> postComments(@Body PublishEntity publishEntity);

    /** 获取收藏列表*/
    @GET("1/classes/News?order=-createdAt")
    Call<QueryResult<NewsEntity>> getLikedList(
            @Query("limit") int limit,
            @Query("skip") int skip,
            @Query("where") InQuery where
    );
}
