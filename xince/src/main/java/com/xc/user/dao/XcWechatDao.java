package com.xc.user.dao;

import com.xc.user.entity.XcWechat;

import java.util.List;

public interface XcWechatDao {

    public XcWechat ifExist(XcWechat xcWechat);

    public void insert(XcWechat xcWechat);
}
