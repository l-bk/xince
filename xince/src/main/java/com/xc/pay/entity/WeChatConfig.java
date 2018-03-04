package com.xc.pay.entity;

import com.xc.util.CommonUtils;

public class WeChatConfig {
    public static final String appid = "wxdfd747621d9b1cfd"; //appId
    public static final String secret= "a7f2d3589a8449be92ba591d52a7e428";//appSecred
    public static final String mchId = "1498901782"; //商户号
    public static final String key ="123aB18826107953123aB18826107953"; //key为商户平台设置的密钥key
    public static final String tradeType="JSAPI";
    public static final String notifyUrl="https://www.magicdn.club/xince/WechatPay/payNotify.do";//回调url
    public static final String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";//订单url
    private String body;
    private String outTradeNo;
    private int totalFee;
    private String spbillCreateIp;


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }
}
