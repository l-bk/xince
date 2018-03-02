package com.xc.pay.controller;

import com.github.pagehelper.StringUtil;
import com.xc.pay.entity.WeChatConfig;
import com.xc.pay.entity.XcTestOrder;
import com.xc.pay.service.WechatPayService;
import com.xc.user.entity.XcWechat;
import com.xc.user.service.RedisService;
import com.xc.user.service.XcWechatService;
import com.xc.util.AjaxJSON;
import com.xc.util.CommonUtils;
import com.xc.util.PayUtil;
import com.xc.util.RequestUtils;
import net.sf.json.JSONObject;
import org.omg.CORBA.Object;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/WechatPay")
public class WechatPayController {

    @Resource
    private RedisService redisService;

    @Resource
    private XcWechatService xcWechatService;

    @Resource
    private WechatPayService wechatPayService;

    @Resource
    private HttpServletRequest request;

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
                //自己创建订单（为了获取商户订单号）
                Map<String, java.lang.Object> newMap = wechatPayService.createOrder(xcWechat,xcTestOrder);
                if(newMap !=  null){
                    String body=(String)newMap.get("testSubject");
                    String outTradeNo=(String)newMap.get("orderId");
                    int totalFee=(Integer)newMap.get("price");
                    String spbillCreateIp =PayUtil.getRemortIP(request);
                    String nonceStr= CommonUtils.getRandomString(32);
                    Map<String,String> map = new HashMap<String, String>();
                    map.put("appid",WeChatConfig.appid);
                    map.put("body",body);
                    map.put("mch_id",WeChatConfig.mchId);
                    map.put("nonce_str",nonceStr);
                    map.put("notify_url",WeChatConfig.notifyUrl);
                    map.put("openid",openId);
                    map.put("out_trade_no",outTradeNo);
                    map.put("spbill_create_ip",spbillCreateIp);
                    map.put("total_fee",String.valueOf(totalFee));
                    map.put("trade_type",WeChatConfig.tradeType);

                    Map<String,String>  map1 = PayUtil.paraFilter(map);
                    String mapStr=PayUtil.createLinkString(map1);
                    //获取签名
                    String sign=PayUtil.sign(mapStr,WeChatConfig.key,"utf-8").toUpperCase();
                    map.put("sign",sign);

                    String xml = "<xml>" + "<appid>" + WeChatConfig.appid + "</appid>"
                            + "<body>" + body + "</body>"
                            + "<mch_id>" + WeChatConfig.mchId + "</mch_id>"
                            + "<nonce_str>" + nonceStr + "</nonce_str>"
                            + "<notify_url>" + WeChatConfig.notifyUrl + "</notify_url>"
                            + "<openid>" + openId + "</openid>"
                            + "<out_trade_no>" + outTradeNo + "</out_trade_no>"
                            + "<spbill_create_ip>" + spbillCreateIp + "</spbill_create_ip>"
                            + "<total_fee>" + totalFee + "</total_fee>"
                            + "<trade_type>" + WeChatConfig.tradeType + "</trade_type>"
                            + "<sign>" + sign + "</sign>"
                            + "</xml>";
                    //访问连接
                    String resultCode = PayUtil.httpRequest(WeChatConfig.pay_url,"POST",xml);
                    Map map2 = PayUtil.doXMLParse(resultCode);
                    Map <String, java.lang.Object> response= new HashMap<String, java.lang.Object>();
                    if(map2 != null && "SUCCESS".equals((String)map2.get("return_code"))){
                        String prepay_id = (String) map.get("prepay_id");
                        response.put("nonceStr",nonceStr);
                        response.put("package","prepay_id"+prepay_id);
                        Long timeStamp = System.currentTimeMillis()/1000;
                        response.put("timeStamp",timeStamp+"");
                        String stringSignTemp = "appId=" + WeChatConfig.appid + "&nonceStr=" + nonceStr + "&package=prepay_id=" + prepay_id+ "&signType=MD5&timeStamp=" + timeStamp;
                        String paySign = PayUtil.sign(stringSignTemp, WeChatConfig.key, "utf-8").toUpperCase();
                        response.put("paySign",paySign);
                        response.put("appid",WeChatConfig.appid);
                        result.setObj(response);
                        result.setSuccess(true);
                    }
                }else {
                    result.setSuccess(false);
                    result.setMsg("订单创建失败");
                }

                return result;
            }


        }catch(Exception e){
            result.setSuccess(false);
            result.setMsg("生成订单失败");
        }
        return result;
    }


    @RequestMapping(value="/payNotify.do",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public void payNotify(HttpServletRequest request) throws Exception {
        BufferedReader br=new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line="";
        StringBuilder sb= new StringBuilder();
        if((line = br.readLine()) != null){
            sb.append(line);
        }
        Map map = PayUtil.doXMLParse(sb.toString());
        if(map != null && "SUCCESS".equals((String)map.get("return_code"))){
            if(PayUtil.verify(PayUtil.createLinkString(map),(String)map.get("sign"),WeChatConfig.key,"utf-8")){
                XcTestOrder xcTestOrder = new XcTestOrder();
                xcTestOrder.setOrderId((String)map.get("out_trade_no"));
                xcTestOrder.setOrderStatus("1");

            }
        }
    }
}
