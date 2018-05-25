package com.hzhanghuan.hhSurvey.evaluation.biz;


import hanghuan.product.evaluation.entity.ProEvaluation;
import hanghuan.product.evaluation.entity.ProEvaluationRecordInfo;
import hanghuan.product.evaluation.entity.ProEvaluationResult;
import org.qsjy.commons.base.entity.PageBean;

import java.util.HashMap;
import java.util.List;


public interface ProEvaluationBiz {

	public ProEvaluation findProEvaluationById(Long id);
	public ProEvaluation findProEvaluationByName(String name);
	public void copyProEvaluationById(Long id);
	public List<ProEvaluation> findProEvaluationsByLabelId(HashMap<String, Object> hashMap);
	
	public void addProEvaluation(ProEvaluation proEvaluation);
	public void insertLabelToEvaluation(Long labelId);
	public void deleteProEvaluation(Long id);
	public void deleteLabelToEvaluationById(Long evaluationId);
	public void deleteUserToProEvaluationByEvaluationId(Long evaluationId);
	public void updateProEvaluation(HashMap<String, Object> hashMap);
	public void updateLabelToEvaluationByEvalationId(HashMap<String, Object> hashMap);
	public String selectCommentsByUserId(HashMap<String, Object> hashMap);

	public List<ProEvaluation> listProEvaluations(HashMap<String, Object> hashMap);
	public List<ProEvaluation> listProEvaluationsByPage(HashMap<String, Object> hashMap);
	
	public List<ProEvaluation> getApprovalApplyListByConditions(HashMap<String, Object> hashMap);

	List<ProEvaluation> listProEvaluationsByConditions(HashMap<String, Object> hashMap);

	void addUserToEvaluation(HashMap<String, Object> hashMap);

	List<ProEvaluation> listProEvaluationsByUserIdByPage(
			HashMap<String, Object> hashMap);

	List<ProEvaluationResult> listProEvaluationResultsByEvaluationId(
			HashMap<String, Object> hashMap);

	Integer countProEvaluationsByConditions(
			HashMap<String, Object> hashMap);

	void deleteUsersByEvaluationId(Long id);

	List<ProEvaluationRecordInfo> selectRecordsFinishedWithUser(HashMap<String, Object> hashMap);

	public PageBean<ProEvaluation> getProEvaluationsByConditions(HashMap<Object, Object> hashMap);


}

