package com.xc.test.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xc.test.dao.XcTestInfoDao;
import com.xc.test.entity.XcTestInfo;
import com.xc.test.service.XcTestInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service("XcTestInfoService")
public class XcTestInfoServiceImpl  implements XcTestInfoService{

    @Resource
    private XcTestInfoDao xcTestInfoDao;

    public PageInfo<XcTestInfo> findList(XcTestInfo xcTestInfo, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<XcTestInfo> pageList = new PageInfo<XcTestInfo>(xcTestInfoDao.findList(xcTestInfo));
        return pageList;
    }

    public Map<String, Object> selectDetails(XcTestInfo xcTestInfo) {
        if(null == xcTestInfo ){
            return  null;
        }
        return xcTestInfoDao.selectDetails(xcTestInfo);
    }
}
