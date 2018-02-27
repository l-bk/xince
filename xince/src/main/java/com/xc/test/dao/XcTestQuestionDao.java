package com.xc.test.dao;

import com.xc.test.entity.XcTestQuestion;

import java.util.Map;

public interface XcTestQuestionDao {
    public Map<String,Object> selectByTestId(XcTestQuestion xcTestQuestion);

    public int selectCountByTestId(Integer testId);

    public XcTestQuestion selectByOptionsId(Integer optionsId);

    public  Map<String,Object> selectByQuestionId(Integer questionId);
}
