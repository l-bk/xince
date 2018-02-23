package com.xc.user.service.impl;

import com.xc.user.service.RedisService;
import com.xc.util.QueueList;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate redisTemplate;


    public String getOpenid(String trd_session) {
        return  (String) redisTemplate.opsForHash().get("wechat:" + trd_session,"openid");
    }

    public String checkWeChatLogin(String trd_session) {
        return redisTemplate.opsForHash().entries("wechat:" + trd_session).isEmpty()?"noLogin":"Logined";
    }

    //插入搜索历史
    public int insertHistory(String trd_session, String method,String title) {
        if(redisTemplate.opsForHash().entries("wechat:" + trd_session).isEmpty()){
            return 0;
        }
        else {
            String sereach = (String) redisTemplate.opsForHash().get("wechat:" + trd_session, method + ":search");
            if (sereach == null || sereach.equals("")) {
                List<String> list = new ArrayList<String>();
                list.add(title);
                redisTemplate.opsForHash().put("wechat:" + trd_session, method + ":search", list.toString());
                return 1;
            }
            else {
                QueueList ql = new QueueList();
                ql.setMax(6);
                ql.setList(sereach);
                ql.append(title);
                redisTemplate.opsForHash().put("wechat:" + trd_session, method + ":search", ql.toString());
                return 1;
            }
        }
    }

    public String getSearch(String trd_session,String method){
        if(redisTemplate.opsForHash().entries("wechat:" + trd_session).isEmpty()){
            return null;
        }
        else {
            String search = (String) redisTemplate.opsForHash().get("wechat:" + trd_session, method + ":search");
            QueueList ql = new QueueList();
            ql.setList(search);
            Collections.reverse(ql.getList());
            return (ql.toString());
        }
    }

    public int clearSearch(String trd_session,String method) {
        if(redisTemplate.opsForHash().entries("wechat:"+trd_session).isEmpty()){
            return 0;
        }
        else {
            redisTemplate.opsForHash().put("wechat:" + trd_session,method + ":search","");
            return 1;
        }
    }
}
