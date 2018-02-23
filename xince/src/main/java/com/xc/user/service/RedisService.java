package com.xc.user.service;

public interface RedisService {

    public String getOpenid(String trd_session);

    public String checkWeChatLogin(String trd_session);


}
