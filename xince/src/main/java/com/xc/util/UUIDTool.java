package com.xc.util;


import com.xc.pay.entity.WeChatConfig;

import java.util.Date;
import java.util.UUID;

public class UUIDTool {


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




}