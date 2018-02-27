package com.xc.test.service.Impl;

import com.xc.test.dao.XcTestOptionsDao;
import com.xc.test.entity.XcTestOptions;
import com.xc.test.service.XcTestOptionsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("XcTestOptions")
public class XcTestOptionsServiceImpl implements XcTestOptionsService{

    @Resource
    private XcTestOptionsDao xcTestOptionsDao;

    public List<XcTestOptions> selectByQuestionId(Integer questionId) {
        if(questionId == null){
            return  null;
        }
        return xcTestOptionsDao.selectByQuestionId(questionId);
    }

    public XcTestOptions selectByOptionsId(Integer optionsId) {
        if( null == optionsId){
            return null;
        }
        return xcTestOptionsDao.selectByOptionsId(optionsId);
    }

}
