package com.hzhanghuan.hhSurvey.evaluation.dao;


import hanghuan.product.evaluation.entity.ProEvaluationTemplate;

import java.util.HashMap;
import java.util.List;

public interface ProEvaluationTemplateDao {
	public ProEvaluationTemplate findProEvaluationTemplateById(Long id);
	
	public void addProEvaluationTemplate(ProEvaluationTemplate proEvaluationTemplate);
	public void updateProEvaluationTemplate(ProEvaluationTemplate proEvaluationTemplate);
	public void deleteProEvaluationTemplate(Long id);
	
	public List<ProEvaluationTemplate> listProEvaluationTemplatesByConditions(HashMap<String, Object> hashMap);

}
