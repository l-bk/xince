package com.xc.user.service;

public interface RedisService {

    public String getOpenid(String trd_session);

    public String checkWeChatLogin(String trd_session);

    public String getSearch(String trd_session, String method);

    public int insertHistory(String trd_session, String method, String title);

    public int clearSearch(String trd_session, String method);

}
