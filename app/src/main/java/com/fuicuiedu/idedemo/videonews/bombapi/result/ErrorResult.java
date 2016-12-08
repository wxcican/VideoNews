package com.fuicuiedu.idedemo.videonews.bombapi.result;

/**
 * 请求失败时返回的结果实体类
 */
public class ErrorResult {

    private int code;

    private String error;

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }
}
