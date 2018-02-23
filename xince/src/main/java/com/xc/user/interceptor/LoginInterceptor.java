package com.xc.user.interceptor;

import com.xc.user.service.RedisService;
import com.xc.util.AjaxJSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String trd_session = httpServletRequest.getParameter("trd_session");
        AjaxJSON ajs = new AjaxJSON();
        String openid = trd_session == null?null:redisService.getOpenid(trd_session);
        System.out.println(openid);
        if(openid == null){
            ajs.setSuccess(false);
            ajs.setMsg("noLogin");
            PrintWriter out = httpServletResponse.getWriter();
            out.print(JSONObject.fromObject(ajs));
            return false;
        }
        else {
            httpServletRequest.setAttribute("openid",openid);
            return true;
        }
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
