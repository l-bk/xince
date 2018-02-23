package com.xc.module.dao;

import com.xc.module.entity.XcModule;

import java.util.List;
import java.util.Map;

public interface XcModuleDao {
    public List<XcModule> findList(XcModule xcModule);
    public Map<String,Object> selectDetails(XcModule xcModule);
}
