package com.xc.util;


import com.xc.pay.entity.XcChatConfig;

import java.util.Date;
import java.util.UUID;

public class UUIDTool {
    private static int num=1;

    public UUIDTool() {
    }
    /**
     * 自动生成32位的UUid，对应数据库的主键id进行插入用。
     * @return
     */
    public static String getUUID() {

        return UUID.randomUUID().toString().replace("-", "");

    }

    public static String getUUID(String name){
        UUID uuid=UUID.nameUUIDFromBytes(name.getBytes());
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }

    public static String getOrderId(){
        long now = new Date().getTime();
        String str = XcChatConfig.mchId +now+num;//商户号+当前时间戳+自增数字
        num++;
        return  str;
    }


}