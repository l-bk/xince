package com.xc.pay.service;

import com.xc.pay.entity.XcTestOrder;
import com.xc.user.entity.XcWechat;

public interface WechatPayService {
    public String createOrder(XcWechat xcWechat,XcTestOrder xcTestOrder);

}
