package com.xc.module.controller;

import com.xc.module.entity.XcModule;
import com.xc.module.service.XcModuleService;
import com.xc.util.AjaxJSON;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.json.JsonObject;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RequestMapping("/module")
public class XcModuleController {

    @Resource
    private XcModuleService xcModuleService;

    @RequestMapping(value="/findList.do",method = RequestMethod.GET,produces ="application/json")
    @ResponseBody
    public AjaxJSON findList(@RequestParam Map<String,Object> param){
        AjaxJSON result =new AjaxJSON();
        try{
            XcModule xcModule=new XcModule();
            xcModule.setModuleStatus("1");//上架
            List<XcModule> list = xcModuleService.findList(xcModule);
            result.setObj(list);
            result.setSuccess(true);
            result.setMsg("查询成功");
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("查询失败");
        }
        return result;
    }

    @RequestMapping(value="/selectDetails.do",method = RequestMethod.POST,produces = "application/json")
    @ResponseBody
    public AjaxJSON selectDetails(@RequestParam Map<String,Object>param,@RequestBody AjaxJSON json){
        AjaxJSON result =new AjaxJSON();
        try{
            XcModule xcModule = (XcModule) JSONObject.toBean(JSONObject.fromObject(json.getObj()),XcModule.class);
            Map<String,Object> map =xcModuleService.selectDetails(xcModule);
            result.setMsg("查询成功");
            result.setSuccess(true);
            result.setObj(map);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("查询失败");
        }
        return result;
    }
}
