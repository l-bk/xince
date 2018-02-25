package com.xc.test.service;

import com.github.pagehelper.PageInfo;
import com.xc.test.entity.XcTestInfo;

import java.util.Map;

public interface XcTestInfoService {

    public PageInfo<XcTestInfo> findList(XcTestInfo xcTestInfo,int pageNum,int pageSize);

    public Map<String,Object> selectDetails(XcTestInfo xcTestInfo);

}
