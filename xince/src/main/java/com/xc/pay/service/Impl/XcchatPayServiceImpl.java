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

    public Map<String,Object> createOrder(XcWechat xcWechat,XcTestOrder xcTestOrder,String payType) {
        if(xcWechat == null || xcTestOrder == null || payType == null){
            return null;
        }
        XcWechat newWechat=xcWechatDao.ifExist(xcWechat);
        XcTestInfo xcTestInfo =new XcTestInfo();
        xcTestInfo.setTestId(xcTestOrder.getTestId());
        Map<String,Object> newMap= xcTestInfoDao.selectDetails(xcTestInfo);
        if(newWechat != null && newMap != null ){
            xcTestOrder.setUserId(newWechat.getUserId());
            xcTestOrder.setTestId(xcTestOrder.getTestId());
            if("1".equals(payType)){
                xcTestOrder.setOrderName(String.valueOf(newMap.get("testSubject")));
            }else if ("2".equals(payType)){
                xcTestOrder.setOrderName(String.valueOf(newMap.get("testSubject"))+"-打赏订单");
            }
            xcTestOrder.setOrderStatus("0");
            String orderId = PayUtil.getOrderId();
            xcTestOrder.setOrderId(orderId);
           int num = xcTestOrderDao.insert(xcTestOrder);
           if(num == 1){
               newMap.put("orderId",orderId);
               newMap.put("testSubject",String.valueOf(newMap.get("testSubject")));
               newMap.put("price",String.valueOf(newMap.get("testPreferentialPrice")));
               return newMap;
           }
        }
        return null;
    }
}
