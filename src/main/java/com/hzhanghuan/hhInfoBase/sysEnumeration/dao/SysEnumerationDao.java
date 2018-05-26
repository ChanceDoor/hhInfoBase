package com.hzhanghuan.hhInfoBase.sysEnumeration.dao;

import com.hzhanghuan.hhCommonBase.dao.BaseDao;
import com.hzhanghuan.hhInfoBase.sysEnumeration.entity.SysEnumeration;

public interface SysEnumerationDao  extends BaseDao<SysEnumeration> {

    public SysEnumeration findEnumerationByValue(String value);
}

