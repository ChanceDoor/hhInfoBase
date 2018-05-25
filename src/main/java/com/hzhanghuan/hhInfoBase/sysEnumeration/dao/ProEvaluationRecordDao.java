package com.hzhanghuan.hhSurvey.evaluation.dao;

import hanghuan.product.evaluation.entity.ProEvaluationRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public interface ProEvaluationRecordDao {
	public ProEvaluationRecord findProEvaluationRecordById(Long id);
	public ProEvaluationRecord findProEvaluationRecordByName(String name);
	
	public void addProEvaluationRecord(ProEvaluationRecord proEvaluationRecord);
	public void updateProEvaluationRecord(ProEvaluationRecord proEvaluationRecord);
	public void deleteProEvaluationRecord(Long id);
	
	public List<ProEvaluationRecord> listProEvaluationRecords(HashMap<String, Object> hashMap);
	public List<ProEvaluationRecord> listProEvaluationRecordsByPage(HashMap<String, Object> hashMap);

	List<ProEvaluationRecord> listProEvaluationRecordsByConditions(
			HashMap<String, Object> hashMap);

	Integer countProEvaluationRecordsByConditions(
			HashMap<String, Object> hashMap);

	ProEvaluationRecord findProEvaluationRecordByConditions(HashMap<String, Object> hashMap);

	List<HashMap<Object,Object>> getSimpleResult(HashMap<String, Object> hashMap);

	BigDecimal findSysRoleWeight(Long id)throws Exception;
}

