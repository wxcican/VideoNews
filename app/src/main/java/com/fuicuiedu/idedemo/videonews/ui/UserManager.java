package com.fuicuiedu.idedemo.videonews.ui;

/**
 * 很简单的用户管理类。
 * TODO: 使用SharedPreferences来存储用户登录信息，从而可以自动登录
 */
public class UserManager {

    private static UserManager sInstance;

    public static UserManager getInstance(){
        if (sInstance == null) {
            sInstance = new UserManager();
        }
        return sInstance;
    }

    private String username;
    private String objectId;

    private UserManager(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public boolean isOffline(){
        return username == null || objectId == null;

    }

    public void clear(){
        username = null;
        objectId = null;
    }
}
