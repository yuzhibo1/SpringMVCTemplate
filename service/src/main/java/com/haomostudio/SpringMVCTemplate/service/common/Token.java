package com.haomostudio.SpringMVCTemplate.service.common;

/**
 *
 * 保存各系统之间的TOKEN的JAVA BEAN
 *
 * Created by steven on 2016/1/6.
 */
public class Token {


    private String userId;

    private String token;

    private String seaFileToken;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSeaFileToken() {
        return seaFileToken;
    }

    public void setSeaFileToken(String seaFileToken) {
        this.seaFileToken = seaFileToken;
    }
}
