package com.hzhanghuan.hhInfoBase.sysEnumeration.biz;

import com.hzhanghuan.hhCommonBase.entity.PageBean;
import com.hzhanghuan.hhInfoBase.sysEnumeration.entity.SysEnumeration;

import java.util.HashMap;
import java.util.List;


public interface SysEnumerationBiz {
	public void addSysEnumeration(SysEnumeration sysEnumeration);
	public void delSysEnumeration(SysEnumeration sysEnumeration);
	public void updateSysEnumeration(SysEnumeration sysEnumeration);
	public SysEnumeration findSysEnumerationById(Long id);
	public SysEnumeration findEnumerationByValue(String value);
	public List<SysEnumeration> getEnumerations();
	public int countSysEnumerations(HashMap<Object, Object> hashMap);
	public List<SysEnumeration> listSysEnumerationsByConditions(HashMap<Object, Object> hashMap);
	
	/***
	 * 根据条件获取所有数据字典:enumerationKeyword,value,description
	 * @params HashMap<String,Object> hashMap
	 * @return List<SysEnumeration>
	 */
	public PageBean<SysEnumeration> getSysEnumerationsPageBeanByConditions(HashMap<Object,Object> hashMap);
	
}

