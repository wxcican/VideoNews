package com.fuicuiedu.idedemo.videonews.bombapi.other;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Damon on 2016/12/25.
 */

public class NewsPointer {

    @SerializedName("__type")
    private String type;

    private String className;

    private String objectId;

    public NewsPointer(String newsId) {
        type = "Pointer";
        className = "_User";
        objectId = newsId;
    }
}
