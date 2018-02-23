package com.xc.module.service.Impl;

import com.xc.module.dao.XcModuleDao;
import com.xc.module.entity.XcModule;
import com.xc.module.service.XcModuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("XcModuleService")
public class XcModuleServiceImpl implements XcModuleService {

    @Resource
    private XcModuleDao moduleDao;

    public List<XcModule> findList(XcModule xcModule) {
        return moduleDao.findList(xcModule);
    }

    public Map<String, Object> selectDetails(XcModule xcModule) {
        return moduleDao.selectDetails(xcModule);
    }
}
