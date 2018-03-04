package com.xc.pay.service;

import com.xc.pay.entity.XcTestOrder;
import com.xc.user.entity.XcWechat;

import java.util.Map;

public interface WechatPayService {
    public Map<String,Object> createOrder(XcWechat xcWechat, XcTestOrder xcTestOrder,String payType);

}
