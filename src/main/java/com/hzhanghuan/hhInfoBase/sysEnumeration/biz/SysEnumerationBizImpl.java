package com.hzhanghuan.hhInfoBase.sysEnumeration.biz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.hzhanghuan.hhCommonBase.entity.PageBean;
import com.hzhanghuan.hhInfoBase.sysEnumeration.dao.SysEnumerationDao;
import com.hzhanghuan.hhInfoBase.sysEnumeration.entity.SysEnumeration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class SysEnumerationBizImpl implements SysEnumerationBiz{
	@Resource
	private SysEnumerationDao sysEnumerationDao;
	
	@Override
	public SysEnumeration findSysEnumerationById(Long id) {
		SysEnumeration enumeration = sysEnumerationDao.getById(id);
		return enumeration;
	}

	@Override
	public SysEnumeration findEnumerationByValue(String value) {
		return sysEnumerationDao.findEnumerationByValue(value);
	}

	@Override
	public List<SysEnumeration> getEnumerations() {
		List<SysEnumeration> enumerations = sysEnumerationDao.findAll();
		return enumerations;
	}


	@Override
	public void addSysEnumeration(SysEnumeration sysEnumeration) {
		sysEnumerationDao.save(sysEnumeration);
	}

	@Override
	public void delSysEnumeration(SysEnumeration sysEnumeration) {
		sysEnumerationDao.delete(sysEnumeration.getId());
	}

	@Override
	public void updateSysEnumeration(SysEnumeration sysEnumeration) {
		sysEnumerationDao.update(sysEnumeration);
	}

	@Override
	public int countSysEnumerations(HashMap<Object, Object> hashMap) {
		return sysEnumerationDao.count(hashMap);
	}

	@Override
	public List<SysEnumeration> listSysEnumerationsByConditions(HashMap<Object, Object> hashMap) {
		return sysEnumerationDao.getByCondition(hashMap);
	}

	@Override
	public PageBean<SysEnumeration> getSysEnumerationsPageBeanByConditions(HashMap<Object, Object> hashMap) {
		return sysEnumerationDao.getPageBean(hashMap);
	}



}

