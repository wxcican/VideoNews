package com.fuicuiedu.idedemo.videonews.bombapi.entity;

import java.util.Date;

/**
 * Created by Damon on 2016/12/25.
 */

//"objectId": "D5vlAAAJ",
////        "username": "用户名"
////        "createdAt": "2016-07-11 12:20:45",
////        "updatedAt": "2016-07-11 12:20:47",

public class AuthorEntity {

    private String objectId;
    private String username;
    private Date createdAt;
    private Date updatedAt;

    public String getObjectId() {
        return objectId;
    }

    public String getUsername() {
        return username;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
