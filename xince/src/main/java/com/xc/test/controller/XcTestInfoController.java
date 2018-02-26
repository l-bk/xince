package com.xc.test.controller;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.xc.test.entity.XcTestAnswer;
import com.xc.test.entity.XcTestInfo;
import com.xc.test.entity.XcTestOptions;
import com.xc.test.entity.XcTestQuestion;
import com.xc.test.service.XcTestAnswerService;
import com.xc.test.service.XcTestInfoService;
import com.xc.test.service.XcTestOptionsService;
import com.xc.test.service.XcTestQuestionService;
import com.xc.util.AjaxJSON;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/testInfo")
public class XcTestInfoController {

    @Resource
    private XcTestInfoService xcTestInfoService;

    @Resource
    private XcTestQuestionService xcTestQuestionService;

    @Resource
    private XcTestAnswerService xcTestAnswerService;

    @Resource
    private XcTestOptionsService xcTestOptionsService;


    /*
    测试数据列表接口
     */
    @RequestMapping(value="/findList.do",produces = "application/json",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJSON findList(@RequestParam Map<String,Object> param, @RequestBody AjaxJSON json){
        AjaxJSON result= new AjaxJSON();
        try{
            String pageNum  = (String)param.get("pageNum");
            String pageSize = (String)param.get("pageSize");
            pageNum = pageNum == null ?"0":pageNum;
            pageSize = pageSize == null ?"0":pageSize;
            XcTestInfo xcTestInfo = (XcTestInfo)JSONObject.toBean(JSONObject.fromObject(json.getObj()),XcTestInfo.class);
            PageInfo<XcTestInfo> pageList= xcTestInfoService.findList(xcTestInfo,Integer.valueOf(pageNum),Integer.valueOf(pageSize));
            result.setSuccess(true);
            result.setTotal(pageList.getTotal());
            result.setObj(pageList.getList());
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("查询失败");
        }
        return result;
    }

    /*
    测试数据详情
     */
    @RequestMapping(value="/selectDetails.do",produces = "application/json",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJSON selectDetails (@RequestParam Map<String,Object> param,@RequestBody AjaxJSON json){
        AjaxJSON result =new AjaxJSON();
        try{
            XcTestInfo xcTestInfo = (XcTestInfo)JSONObject.toBean(JSONObject.fromObject(json.getObj()),XcTestInfo.class);
            Map<String,Object> map =xcTestInfoService.selectDetails(xcTestInfo);
            result.setMsg("查询成功");
            result.setSuccess(true);
            result.setObj(map);
        }catch(Exception e){
            result.setSuccess(false);
            result.setMsg("查询失败");
        }
        return result;
    }

    /*
    首页弹窗显示测试数据
     */
    @RequestMapping(value="/selectShow.do",produces = "application/json",method = RequestMethod.GET)
    @ResponseBody
    public AjaxJSON selectShow (@RequestParam Map<String,Object>param){
        AjaxJSON result =new AjaxJSON();
        try{
            XcTestInfo xcTestInfo = new XcTestInfo();
            xcTestInfo.setIfShow("1");
            PageInfo<XcTestInfo> pageInfo= xcTestInfoService.findList(xcTestInfo,0,0);
            result.setSuccess(true);
            result.setMsg("查询成功");
            result.setObj(pageInfo.getList());
            result.setTotal(pageInfo.getTotal());
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("查询失败");
        }
        return result;
    }

    @RequestMapping(value="/selectQuestion.do",produces = "application/json",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJSON selectQuestion(@RequestParam Map<String,Object>param,@RequestBody AjaxJSON json){
        AjaxJSON result =new AjaxJSON();
        try{
            //testId optionsId
            XcTestQuestion xcTestQuestion =(XcTestQuestion)JSONObject.toBean(JSONObject.fromObject(json.getObj()),XcTestQuestion.class);
            XcTestOptions xcTestOptions=(XcTestOptions)JSONObject.toBean(JSONObject.fromObject(json.getObj()),XcTestOptions.class);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("查询失败");
        }
        return result;
    }

}
