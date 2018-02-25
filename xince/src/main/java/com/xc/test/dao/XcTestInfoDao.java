package com.xc.test.dao;

import com.xc.test.entity.XcTestInfo;

import java.util.List;
import java.util.Map;

public interface  XcTestInfoDao {
    public List<XcTestInfo> findList(XcTestInfo xcTestInfo);
    public Map<String,Object> selectDetails(XcTestInfo xcTestInfo);
}
