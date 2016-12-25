package com.fuicuiedu.idedemo.videonews.bombapi.entity;

import java.util.Date;

/**
 * Created by Damon on 2016/12/25.
 */

//"author": { // 评论作者
//        "objectId": "D5vlAAAJ",
//        "username": "用户名"
//        "createdAt": "2016-07-11 12:20:45",
//        "updatedAt": "2016-07-11 12:20:47",
//        },
//        "content": "评论内容",
//        "createdAt": "2016-07-11 12:22:03",
//        "objectId": "ioqs000W",
//        "updatedAt": "2016-07-11 12:23:10"


public class CommentsEntity {

    private AuthorEntity author;
    private String content;
    private Date  createdAt;
    private String objectId;
    private Date updatedAt;

    public AuthorEntity getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }
}
