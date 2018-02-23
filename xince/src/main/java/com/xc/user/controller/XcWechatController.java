package com.xc.user.controller;

import com.xc.user.service.RedisService;
import com.xc.user.service.XcWechatService;
import com.xc.util.AjaxJSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/XcWechat")
@Controller
public class XcWechatController {

    @Resource
    private XcWechatService xcWechatService;

    @Resource
    private RedisService redisService;


    @RequestMapping(value="/WeChatlogin.do",produces="application/json" ,method = RequestMethod.GET)
    @ResponseBody
    public AjaxJSON WeChatlogin(@RequestParam Map<String,Object> params){
        String trd_session = (String)params.get("trd_session");
        AjaxJSON res = new AjaxJSON();
        try{
            if(trd_session != null){
                String result = redisService.checkWeChatLogin(trd_session);
                res.setMsg(result.equals("Logined")?"Login":"noLogin");
                res.setSuccess(result.equals("Logined")?true:false);
            }
            else{
                String code = (String)params.get("code");
                String result = xcWechatService.WeChatLogin(code);
                Map<String,Object> map = new HashMap<String, Object>();
                res.setSuccess(result.equals("error")?false:true);
                map.put("trd_session",result.equals("error")?null:result);
                res.setObj(map);
            }
        }catch (Exception e){
            res.setSuccess(false);
            res.setMsg(e.getMessage());
        }
        return res;
    }
}
