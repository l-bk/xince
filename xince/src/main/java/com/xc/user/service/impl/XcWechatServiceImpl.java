package com.xc.user.service.impl;

import com.xc.user.dao.XcWechatDao;
import com.xc.user.entity.XcWechat;
import com.xc.user.service.XcWechatService;
import com.xc.util.RequestUtils;
import com.xc.util.UUIDTool;
import net.sf.json.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service("XcWechatService")
public class XcWechatServiceImpl implements XcWechatService {

    @Resource
    private XcWechatDao xcWechatDao;

    @Resource
    private RedisTemplate redisTemplate;

    //初始化redis用户存储时间
    private static final int SESSION_TIME = 20;

    private static final String appid = "wxa40025bf8ead1e26";
    private static final String app_se= "3d18bab9a9c55408c573200113139f6f";
    private static final String url="https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";



    public String WeChatLogin(String code) {
        String app_url = url.replace("APPID",appid).replace("SECRET",app_se).replace("JSCODE",code);
        JSONObject resultJSON = JSONObject.fromObject(RequestUtils.httpRequest(app_url));
        if(resultJSON.containsKey("errcode")){
            return "error";
        }
        else {
            String openid = resultJSON.getString("openid");
            String session_key = resultJSON.getString("session_key");
            String trd_sessionid = UUIDTool.getUUID(openid+session_key);
            Map<String,String> map = new HashMap<String, String>();
            map.put("openid",openid);
            map.put("session_key",session_key);
            XcWechat wechat =new XcWechat();
            wechat.setOpenId(openid);
            if(xcWechatDao.ifExist(wechat).size() == 0){
                //新增用户
                XcWechat newXcWechat =new XcWechat();
                newXcWechat.setOpenId(openid);
                newXcWechat.setUserSex("0");
                xcWechatDao.insert(newXcWechat);
            }
            redisTemplate.opsForHash().putAll("wechat:"+trd_sessionid,map);
            redisTemplate.expire(trd_sessionid,SESSION_TIME, TimeUnit.DAYS);
            return trd_sessionid;
        }
    }
}
