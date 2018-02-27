package com.xc.test.dao;

import com.xc.test.entity.XcTestOptions;

import java.util.List;

public interface XcTestOptionsDao {
    public List<XcTestOptions> selectByQuestionId(Integer questionId);

    public XcTestOptions selectByOptionsId(Integer optionsId);
}
