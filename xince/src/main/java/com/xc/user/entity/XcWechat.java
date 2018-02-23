package com.xc.user.entity;

public class XcWechat {

    private Integer userId;
    private String openId;
    private String userSex;
    private String userName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public XcWechat(Integer userId, String openId, String userSex, String userName) {
        this.userId = userId;
        this.openId = openId;
        this.userSex = userSex;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "XcWechat{" +
                "userId=" + userId +
                ", openId='" + openId + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public XcWechat() {
    }
}
