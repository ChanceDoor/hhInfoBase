package com.hzhanghuan.hhSurvey.evaluation.biz;


import hanghuan.product.evaluation.entity.ProEvaluationRecord;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


public interface ProEvaluationRecordBiz {

	public ProEvaluationRecord findProEvaluationRecordById(Long id);
	public ProEvaluationRecord findProEvaluationRecordByName(String name);
	
	public void addProEvaluationRecord(ProEvaluationRecord proEvaluationRecord);
	public void deleteProEvaluationRecord(Long id);
	public void updateProEvaluationRecord(ProEvaluationRecord proEvaluationRecord);
	
	
	public List<ProEvaluationRecord> listProEvaluationRecordsByConditions(HashMap<String, Object> hashMap);
	public List<ProEvaluationRecord> listProEvaluationRecordsByPage(HashMap<String, Object> hashMap);

	Integer countProEvaluationRecordsByConditions(
			HashMap<String, Object> hashMap);

	ProEvaluationRecord findProEvaluationRecordByConditions(HashMap<String, Object> hashMap);

	List<HashMap<Object,Object>> getSimpleResult(HashMap<String, Object> hashMap);

	BigDecimal findSysRoleWeightBySysUser(Long id)throws Exception;
}

