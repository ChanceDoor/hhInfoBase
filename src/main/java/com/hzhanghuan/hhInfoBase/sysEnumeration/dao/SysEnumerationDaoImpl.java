package com.hzhanghuan.hhInfoBase.sysEnumeration.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.hzhanghuan.hhCommonBase.dao.BaseDaoImpl;
import com.hzhanghuan.hhInfoBase.sysEnumeration.entity.SysEnumeration;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class SysEnumerationDaoImpl extends BaseDaoImpl<SysEnumeration> implements SysEnumerationDao {
	@Resource
	private SqlSessionTemplate  sqlSessionTemplate;

	@Override
	public SysEnumeration findEnumerationByValue(String value) {
		return sqlSessionTemplate.selectOne("findEnumerationByValue",value);
	}
}

