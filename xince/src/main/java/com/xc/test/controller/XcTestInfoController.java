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
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.rmi.CORBA.Util;
import java.math.BigDecimal;
import java.util.HashMap;
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

    /*
    获取问题以及选项接口
     */
    @RequestMapping(value="/selectQuestion.do",produces = "application/json",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJSON selectQuestion(@RequestParam Map<String,Object>param,@RequestBody AjaxJSON json){
        AjaxJSON result =new AjaxJSON();
        try{
            //testId optionsId
            XcTestQuestion xcTestQuestion =(XcTestQuestion)JSONObject.toBean(JSONObject.fromObject(json.getObj()),XcTestQuestion.class);
            XcTestOptions xcTestOptions=(XcTestOptions)JSONObject.toBean(JSONObject.fromObject(json.getObj()),XcTestOptions.class);
            int i=1;
            Map<String,Object> map= new HashMap<String, Object>();
            XcTestOptions newOption = new XcTestOptions();
        if(null != xcTestOptions.getOptionsId() && !"".equals(xcTestOptions.getOptionsId())){ //不是第一个问题，就传optionsId
                 newOption = xcTestOptionsService.selectByOptionsId(xcTestOptions.getOptionsId());
                if("1".equals(newOption.getIfSkip())){//跳题，直接拿问题中的skipQuestionId
                    map=xcTestQuestionService.selectByQuestionId(newOption.getSkipQuestionId());
                }else if("0".equals(newOption.getIfSkip())){//不跳题
                    XcTestQuestion  newQuestion=xcTestQuestionService.selectByOptionsId(xcTestOptions.getOptionsId());
                    i=newQuestion.getQuestionNum()+1;
                    xcTestQuestion.setQuestionNum(i);
                    map= xcTestQuestionService.selectByTestId(xcTestQuestion); //查询问题
                }
            }else{ //获取第一个问题时没传optionsId
                xcTestQuestion.setQuestionNum(i);
                map= xcTestQuestionService.selectByTestId(xcTestQuestion); //查询问题
            }
            if(map.size() != 0){
                List<XcTestOptions> options=xcTestOptionsService.selectByQuestionId((Integer)map.get("questionId"));
                map.put("options",options);
                //判定是否有下一题
                XcTestQuestion newQues =new XcTestQuestion();
                newQues.setQuestionNum((Integer)map.get("questionNum")+1);
                newQues.setTestId(xcTestQuestion.getTestId());
                Map<String,Object> newMap=xcTestQuestionService.selectByTestId(newQues);
                if(newMap != null){//是否最后一题
                    map.put("ifNext","Y");
                }else{//获取最后一题时要判断最后一题的所选选项是否是跳会前面题的情况
                    if("1".equals(newOption.getIfSkip())){
                        map.put("ifNext","Y");
                    }else if("0".equals(newOption.getIfSkip())){
                        map.put("ifNext","N");
                    }
                }
                result.setObj(map);
                result.setSuccess(true);
            }else{
                result.setSuccess(true);
                result.setMsg("不存在测试问题");
                result.setObj(map);
            }

        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("查询失败");
        }
        return result;
    }

    /*
    结果判定接口
     */
    @RequestMapping(value="/selectAnswer.do",produces = "application/json",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJSON selectAnswer (@RequestParam Map<String,Object>param,@RequestBody AjaxJSON json){
        AjaxJSON result = new AjaxJSON();
        try{
            JSONObject newJson = JSONObject.fromObject(json.getObj());
            String pointStr= newJson.getString("pointStr");
            XcTestAnswer xcTestAnswer = (XcTestAnswer)JSONObject.toBean(newJson, XcTestAnswer.class);
            String[] arr= pointStr.split(",");
            int point=0;
            for(int i=0;i<arr.length;i++){
                point += Integer.valueOf(arr[i]);
            }
            xcTestAnswer.setPoint(point);
            Map<String,Object> map= xcTestAnswerService.selectAnswerByPoint(xcTestAnswer);
            result.setSuccess(true);
            result.setObj(map);
        }catch (Exception e){
            result.setSuccess(false);
            result.setMsg("查询失败");
        }
        return  result;
    }
}
