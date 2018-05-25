package com.hzhanghuan.hhSurvey.evaluation.biz;

import hanghuan.product.evaluation.dao.ProEvaluationTemplateDao;
import hanghuan.product.evaluation.entity.ProEvaluationTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 测评模板业务实现层
 * @author chengduo
 * @version 1.0.8 2016-11-13
 */
@Service
@Transactional
public class ProEvaluationTemplateBizImpl implements ProEvaluationTemplateBiz{
	@Resource
	private ProEvaluationTemplateDao proEvaluationTemplateDao;

	@Override
	public ProEvaluationTemplate findProEvaluationTemplateById(Long id) {
		
			return proEvaluationTemplateDao.findProEvaluationTemplateById(id);
	
	}

	@Override
	public void addProEvaluationTemplate(ProEvaluationTemplate proEvaluationTemplate) {
		try {
			proEvaluationTemplateDao.addProEvaluationTemplate(proEvaluationTemplate);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public int deleteProEvaluationTemplate(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateProEvaluationTemplate(ProEvaluationTemplate proEvaluationTemplate) {
		try {
			proEvaluationTemplateDao.updateProEvaluationTemplate(proEvaluationTemplate);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ProEvaluationTemplate> listProEvaluationTemplatesByConditions(
			HashMap<String, Object> hashMap) {
		return proEvaluationTemplateDao.listProEvaluationTemplatesByConditions(hashMap);
	}
}
