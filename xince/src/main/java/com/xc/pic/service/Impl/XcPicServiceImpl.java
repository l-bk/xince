package com.xc.pic.service.Impl;

import com.xc.pic.dao.XcPicDao;
import com.xc.pic.entity.XcPic;
import com.xc.pic.service.XcPicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("XcPicService")
public class XcPicServiceImpl implements XcPicService {
    @Resource
    private XcPicDao picDao;
    public List<XcPic> selectAll(XcPic xcPic) {
        return picDao.selectAll(xcPic);
    }
}
