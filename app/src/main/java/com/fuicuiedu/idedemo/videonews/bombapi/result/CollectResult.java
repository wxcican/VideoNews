package com.fuicuiedu.idedemo.videonews.bombapi.result;

import com.fuicuiedu.idedemo.videonews.bombapi.entity.NewsEntity;

import java.util.List;

/**
 * Created by Damon on 2016/12/25.
 */

public class CollectResult {

    private boolean success;
    private String error;
    private NewsEntity data;

    public boolean isSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public NewsEntity getData() {
        return data;
    }
}
