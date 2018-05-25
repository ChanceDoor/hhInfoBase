package com.hzhanghuan.hhSurvey.evaluation.biz;


import hanghuan.product.evaluation.entity.ProEvaluationTemplate;

import java.util.HashMap;
import java.util.List;

public interface ProEvaluationTemplateBiz {

	public ProEvaluationTemplate findProEvaluationTemplateById(Long id);
	
	public void addProEvaluationTemplate(ProEvaluationTemplate proEvaluationTemplate);
	public int deleteProEvaluationTemplate(Long id);
	public void updateProEvaluationTemplate(ProEvaluationTemplate proEvaluationTemplate);
	public List<ProEvaluationTemplate> listProEvaluationTemplatesByConditions(HashMap<String, Object> hashMap);

	
}
