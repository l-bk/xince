package com.xc.test.service;

import com.xc.test.entity.XcTestAnswer;

import java.util.Map;

public interface XcTestAnswerService {
    public Map<String,Object> selectAnswerByPoint(XcTestAnswer xcTestAnswer);
    public Map<String,Object> selectByAnswId(XcTestAnswer xcTestAnswer);
}
