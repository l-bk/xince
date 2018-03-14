package com.xc.test.service.Impl;

import com.xc.test.dao.XcTestAnswerDao;
import com.xc.test.entity.XcTestAnswer;
import com.xc.test.service.XcTestAnswerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("XcTestAnswerService")
public class XcTestAnswerServiceImpl implements XcTestAnswerService {

    @Resource
    private XcTestAnswerDao xcTestAnswerDao;

    public Map<String,Object> selectAnswerByPoint(XcTestAnswer xcTestAnswer) {
        if(xcTestAnswer == null){
            return null;
        }
        return  xcTestAnswerDao.selectAnswerByPoint(xcTestAnswer);
    }

    public Map<String, Object> selectByAnswId(XcTestAnswer xcTestAnswer) {
        if(null == xcTestAnswer){
            return  null;
        }
        return  xcTestAnswerDao.selectByAnswId(xcTestAnswer);
    }
}
