package com.xc.pay.service.Impl;

import com.xc.pay.dao.XcTestOrderDao;
import com.xc.pay.entity.XcTestOrder;
import com.xc.pay.service.XcTestOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("XcTestOrderService")
public class XcTestOrderServiceImpl implements XcTestOrderService {

    @Resource
    private XcTestOrderDao xcTestOrderDao;

    public int insert(XcTestOrder xcTestOrder) {
        if( null == xcTestOrder){
            return 0;
        }
        return xcTestOrderDao.insert(xcTestOrder);

    }

}
