package com.fuicuiedu.idedemo.videonews.bombapi.entity;

/**
 * 用户实体类(注册时的请求体)
 */

//    {
//        "username" : username,   // 用户名
//        "password" : password    // 密码
//    }

public class UserEntity{

    private String username;

    private String password;

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
