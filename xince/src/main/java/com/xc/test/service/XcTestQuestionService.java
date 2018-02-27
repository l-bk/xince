package com.xc.test.service;

import com.xc.test.entity.XcTestQuestion;

import java.util.Map;

public interface XcTestQuestionService {
    public Map<String,Object> selectByTestId(XcTestQuestion xcTestQuestion);

    public int selectCountByTestId(Integer testId);

    public XcTestQuestion selectByOptionsId(Integer optionsId);

    public  Map<String,Object> selectByQuestionId(Integer questionId);
}
