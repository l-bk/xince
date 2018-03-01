package com.xc.pay.controller;

import com.github.pagehelper.StringUtil;
import com.xc.pay.entity.XcTestOrder;
import com.xc.pay.service.WechatPayService;
import com.xc.user.entity.XcWechat;
import com.xc.user.service.RedisService;
import com.xc.user.service.XcWechatService;
import com.xc.util.AjaxJSON;
import com.xc.util.CommonUtils;
import net.sf.json.JSONObject;
import org.omg.CORBA.Object;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("WechatPay")
public class WechatPayController {

    @Resource
    private RedisService redisService;

    @Resource
    private XcWechatService xcWechatService;

    @Resource
    private WechatPayService wechatPayService;

    @RequestMapping(value="/payOrder.do",produces = "application/json",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJSON payOrder(@RequestParam Map<String,Object> param, @RequestBody AjaxJSON json){
        AjaxJSON result = new AjaxJSON();
        try{
            String trd_session=String.valueOf(param.get("trd_session"));
            //testId
            XcTestOrder xcTestOrder = (XcTestOrder) JSONObject.toBean(JSONObject.fromObject(json.getObj()),XcTestOrder.class);
            //userSex
            XcWechat xcWechat =(XcWechat) JSONObject.toBean(JSONObject.fromObject(json.getObj()),XcWechat.class);
            if(StringUtil.isNotEmpty(trd_session)){
                String openId=redisService.getOpenid(trd_session);
                xcWechat.setOpenId(openId);
                String orderId = wechatPayService.createOrder(xcWechat,xcTestOrder);

            }


            String nonceStr = CommonUtils.getRandomString(32);
            String body ="";//商品描述
            String notifyUrl="";
            String sign="";
            String out_trade_no ="";//商户订单号
            String total_fee = ""; //金额
            String spbill_create_ip = "" ; // 终端ip

        }catch(Exception e){
            result.setSuccess(false);
            result.setMsg("生成订单失败");
        }
        return result;
    }

}
