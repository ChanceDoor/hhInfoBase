package com.hzhanghuan.hhSurvey.evaluation.dao;

import hanghuan.product.evaluation.entity.ProEvaluationRecord;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Repository
public class ProEvaluationRecordDaoImpl implements ProEvaluationRecordDao {
	@Resource
	private SqlSessionTemplate  sqlSession;   

    @Override
	public ProEvaluationRecord findProEvaluationRecordById(Long id) {
		ProEvaluationRecord proEvaluationRecord = null;
		try {
			proEvaluationRecord = (ProEvaluationRecord) sqlSession.selectOne("findProEvaluationRecordById", id); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluationRecord;
	}

    @Override
	public ProEvaluationRecord findProEvaluationRecordByName(String name) {
		ProEvaluationRecord proEvaluationRecord = null;
		try {
			proEvaluationRecord = sqlSession.selectOne("findProEvaluationRecordByName", name);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return proEvaluationRecord;
	}
    

    @Override
	public void addProEvaluationRecord(ProEvaluationRecord proEvaluationRecord) {
		try {
			sqlSession.insert("insertProEvaluationRecord", proEvaluationRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

    @Override
	public void updateProEvaluationRecord(ProEvaluationRecord proEvaluationRecord) {
		try {
			sqlSession.update("updateProEvaluationRecordById", proEvaluationRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

    @Override
	public void deleteProEvaluationRecord(Long id) {
		try {
			sqlSession.delete("deleteProEvaluationRecordById", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

    @Override
	public List<ProEvaluationRecord> listProEvaluationRecords(HashMap<String, Object> hashMap) {
		List<ProEvaluationRecord> proEvaluationRecords = null;
		try {
			proEvaluationRecords=sqlSession.selectList("selectAllProEvaluationRecordsByConditions", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluationRecords ;
	}

    @Override
	public List<ProEvaluationRecord> listProEvaluationRecordsByPage(HashMap<String, Object> hashMap) {
		List<ProEvaluationRecord> proEvaluationRecords = null;
		try {
			proEvaluationRecords = sqlSession.selectList("selectAllProEvaluationRecordsByConditions", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluationRecords;
	}

	@Override
	public List<ProEvaluationRecord> listProEvaluationRecordsByConditions(
			HashMap<String, Object> hashMap) {
		List<ProEvaluationRecord> rs = null;
		try{
			rs = sqlSession.selectList("selectProEvaluationRecordsByConditions", hashMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}


	@Override
	public Integer countProEvaluationRecordsByConditions(
			HashMap<String, Object> hashMap) {
		Integer rs = 0;
		try{
			rs = sqlSession.selectOne("countProEvaluationRecordsByConditions", hashMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public ProEvaluationRecord findProEvaluationRecordByConditions(HashMap<String, Object> hashMap) {
		ProEvaluationRecord proEvaluationRecord = null;
		try {
			proEvaluationRecord = sqlSession.selectOne("findProEvaluationRecordByConditions", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return proEvaluationRecord;
	}

	@Override
	public List<HashMap<Object, Object>> getSimpleResult(HashMap<String, Object> hashMap) {
		List<HashMap<Object, Object>> rs = null;
		try{
			rs = sqlSession.selectList("getProEvaluationSimpleResult", hashMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public BigDecimal findSysRoleWeight(Long id) throws Exception{
		BigDecimal weight = null;
    	try{
			weight = sqlSession.selectOne("findSysRoleWeightByUser",id);
		}catch(Exception e){
    		e.printStackTrace();
		}
		return weight;
	}


}

