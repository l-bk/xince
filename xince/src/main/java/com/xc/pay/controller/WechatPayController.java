package com.xc.pay.controller;

import com.github.pagehelper.StringUtil;
import com.xc.pay.entity.WeChatConfig;
import com.xc.pay.entity.XcTestOrder;
import com.xc.pay.service.WechatPayService;
import com.xc.pay.service.XcTestOrderService;
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
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
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

    @Resource
    private XcTestOrderService xcTestOrderService;

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

            //payType 1.正常支付 2.打赏
            String payType=(String)JSONObject.fromObject(json.getObj()).get("payType");
            String payPrice= (String)JSONObject.fromObject(json.getObj()).get("payPrice");
            if(StringUtil.isNotEmpty(trd_session)){
                String openId=redisService.getOpenid(trd_session);
                xcWechat.setOpenId(openId);
                //修改性别
                if(StringUtil.isNotEmpty(payType) && "1".equals(payType) ){
                    xcWechatService.update(xcWechat);
                }
                //自己创建订单（为了获取商户订单号）
                Map<String, java.lang.Object> newMap = wechatPayService.createOrder(xcWechat,xcTestOrder,payType);
                if(newMap !=  null){
                    String body=(String)newMap.get("testSubject");
                    String outTradeNo=(String)newMap.get("orderId");
                    int totalFee=0;
                    if("1".equals(payType)){

                        totalFee= new BigDecimal ((String)newMap.get("price")).multiply(new BigDecimal("100")).intValue();
                        if(totalFee == 0){
                            result.setMsg("获取不到价格，免费信息不需要正常付费");
                            result.setSuccess(false);
                            return result;
                        }
                    }else if ("2".equals(payType)){
                        if(StringUtil.isNotEmpty(payPrice)){
                            totalFee=new BigDecimal (payPrice).multiply(new BigDecimal("100")).intValue();
                        }else{
                            result.setMsg("打赏金额为空");
                            result.setSuccess(false);
                            return result;
                        }
                    }

                    String spbillCreateIp =PayUtil.getRemortIP(request);
//                    String spbillCreateIp ="218.107.22.182";
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
                    System.out.println("mapStr:---------------"+mapStr);
                    System.out.println("-------------------------------");
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
                    System.out.println("resultCode :-----------------"+resultCode);
                    System.out.println("-------------------------------");
                    Map map2 = PayUtil.doXMLParse(resultCode);
                    Map <String, java.lang.Object> response= new HashMap<String, java.lang.Object>();
                    if(map2 != null && "SUCCESS".equals((String)map2.get("return_code"))){
                        String prepay_id = (String) map2.get("prepay_id");
                        response.put("nonceStr",nonceStr);
                        response.put("package","prepay_id="+prepay_id);
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


    @RequestMapping(value="/payNotify.do",method = RequestMethod.POST)
    @ResponseBody
    public void payNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {

        StringBuffer sb = new StringBuffer() ;
        InputStream is = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String s = "" ;
        while((s=br.readLine())!=null){
            sb.append(s) ;
        }
        String str =sb.toString();
        System.out.println("sb:-----"+str);
        Map map = PayUtil.doXMLParse(str);
        if(map != null && "SUCCESS".equals((String)map.get("return_code"))){
                XcTestOrder xcTestOrder = new XcTestOrder();
                xcTestOrder.setOrderId((String)map.get("out_trade_no"));
                xcTestOrder.setOrderStatus("1");
                xcTestOrderService.update(xcTestOrder);

        }
    }
}
