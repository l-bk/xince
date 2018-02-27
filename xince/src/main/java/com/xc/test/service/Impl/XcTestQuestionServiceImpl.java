package com.xc.test.service.Impl;

import com.xc.test.dao.XcTestQuestionDao;
import com.xc.test.entity.XcTestQuestion;
import com.xc.test.service.XcTestQuestionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("XcTestQuestionService")
public class XcTestQuestionServiceImpl  implements XcTestQuestionService{

    @Resource
    private XcTestQuestionDao xcTestQuestionDao;

    public Map<String, Object> selectByTestId(XcTestQuestion xcTestQuestion) {
        if(null == xcTestQuestion){
            return null;
        }
        return xcTestQuestionDao.selectByTestId(xcTestQuestion);
    }

    public int selectCountByTestId(Integer testId) {
        if(null == testId){
            return 0;
        }
        return xcTestQuestionDao.selectCountByTestId(testId);
    }

    public XcTestQuestion selectByOptionsId(Integer optionsId) {
        if(null == optionsId ){
            return null;
        }
        return xcTestQuestionDao.selectByOptionsId(optionsId);
    }

    public  Map<String,Object> selectByQuestionId(Integer questionId) {
        if(null == questionId){

            return null;
        }
        return  xcTestQuestionDao.selectByQuestionId(questionId);
    }
}
