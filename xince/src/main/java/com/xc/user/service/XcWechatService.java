package com.xc.user.service;

import com.xc.user.entity.XcWechat;
import org.springframework.stereotype.Service;


public interface XcWechatService {

    public String WeChatLogin(String code);

    public XcWechat ifExist(XcWechat xcWechat);
}
