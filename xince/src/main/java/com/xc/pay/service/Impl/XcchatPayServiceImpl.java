package com.xc.pay.service.Impl;

import com.xc.pay.dao.XcTestOrderDao;
import com.xc.pay.entity.XcTestOrder;
import com.xc.pay.service.WechatPayService;
import com.xc.test.dao.XcTestInfoDao;
import com.xc.test.entity.XcTestInfo;
import com.xc.user.dao.XcWechatDao;
import com.xc.user.entity.XcWechat;
import com.xc.util.PayUtil;
import com.xc.util.UUIDTool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("WechatPayService")
public class XcchatPayServiceImpl implements WechatPayService {

    @Resource
    private XcWechatDao xcWechatDao;

    @Resource
    private XcTestOrderDao xcTestOrderDao;

    @Resource
    private XcTestInfoDao xcTestInfoDao;

    public Map<String,Object> createOrder(XcWechat xcWechat,XcTestOrder xcTestOrder) {
        if(xcWechat == null || xcTestOrder == null){
            return null;
        }
        Map<String,Object> map =new HashMap<String, Object>() ;
        XcWechat newWechat=xcWechatDao.ifExist(xcWechat);
        XcTestInfo xcTestInfo =new XcTestInfo();
        xcTestInfo.setTestId(xcTestOrder.getTestId());
        Map<String,Object> newMap= xcTestInfoDao.selectDetails(xcTestInfo);
        if(newWechat != null && map != null){
            xcTestOrder.setUserId(newWechat.getUserId());
            xcTestOrder.setTestId(xcTestOrder.getTestId());
            xcTestOrder.setOrderName(String.valueOf(map.get("testSubject")));
            xcTestOrder.setOrderStatus("0");
            String orderId = PayUtil.getOrderId();
            xcTestOrder.setOrderId(orderId);
           int num = xcTestOrderDao.insert(xcTestOrder);
           if(num == 1){
               newMap.put("orderId",orderId);
               newMap.put("testSubject",String.valueOf(map.get("testSubject")));
               newMap.put("price",String.valueOf(newMap.get("testPreferentialPrice")));
               return newMap;
           }
        }
        return null;
    }
}
