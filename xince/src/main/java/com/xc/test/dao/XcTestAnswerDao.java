package com.xc.test.dao;

import com.xc.test.entity.XcTestAnswer;

import java.util.Map;

public interface XcTestAnswerDao {
    public Map<String,Object> selectAnswerByPoint(XcTestAnswer xcTestAnswer);
    public Map<String,Object> selectByAnswId(XcTestAnswer xcTestAnswer);
}
