package com.xc.module.service;

import com.xc.module.entity.XcModule;

import java.util.List;
import java.util.Map;

public interface XcModuleService {

    public List<XcModule> findList(XcModule xcModule);

    public Map<String,Object> selectDetails(XcModule xcModule);
}
