package com.xc.pic.controller;

import com.xc.pic.entity.XcPic;
import com.xc.pic.service.XcPicService;
import com.xc.test.entity.XcTestInfo;
import com.xc.test.service.XcTestInfoService;
import com.xc.util.AjaxJSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/pic")
public class XcPicController {

    @Resource
    private XcPicService xcPicService;

    @Resource
    private XcTestInfoService testInfoService;

    @RequestMapping(value = "/selectAllPic.do",produces = "application/json",method = RequestMethod.GET)
    @ResponseBody
    public AjaxJSON selectAllPic(@RequestParam Map<String,Object> param){
        AjaxJSON result =new AjaxJSON();
        try{
            XcPic pic = new XcPic();
            pic.setPicStatus("1");
            pic.setPicType("1");
            List<XcPic> newList = new ArrayList<XcPic>();
            List<XcPic> picList = xcPicService.selectAll(pic);
            for(XcPic xcPic : picList){
                XcTestInfo xcTestInfo = new XcTestInfo();
                xcTestInfo.setTestId(xcPic.getTestId());
                Map<String,Object> map = testInfoService.selectDetails(xcTestInfo);
                xcPic.setMap(map);
                newList.add(xcPic);
            }
            result.setMsg("查询成功");
            result.setSuccess(true);
            result.setObj(newList);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("查询失败");
        }
        return  result;
    }
}
