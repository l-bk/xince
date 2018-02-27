package com.xc.test.service;

import com.xc.test.entity.XcTestOptions;

import java.util.List;

public interface XcTestOptionsService {
    public List<XcTestOptions> selectByQuestionId(Integer questionId);

    public XcTestOptions selectByOptionsId(Integer optionsId);
}
