package com.hzhanghuan.hhSurvey.evaluation.dao;

import hanghuan.product.evaluation.entity.ProEvaluationTemplate;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository
public class ProEvaluationTemplateDaoImpl implements ProEvaluationTemplateDao {
	@Resource
	private SqlSessionTemplate  sqlSession;   

    @Override
	public ProEvaluationTemplate findProEvaluationTemplateById(Long id) {
		ProEvaluationTemplate proEvaluationTemplate = null;
		try {
			proEvaluationTemplate = (ProEvaluationTemplate) sqlSession.selectOne("findProEvaluationTemplateById", id); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluationTemplate;
	}
    

    @Override
	public void addProEvaluationTemplate(ProEvaluationTemplate proEvaluationTemplate) {
		try {
			sqlSession.insert("insertProEvaluationTemplate", proEvaluationTemplate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

    @Override
	public void updateProEvaluationTemplate(ProEvaluationTemplate proEvaluationTemplate) {
		try {
			sqlSession.update("updateProEvaluationTemplateById", proEvaluationTemplate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

    @Override
	public void deleteProEvaluationTemplate(Long id) {
		try {
			sqlSession.delete("deleteProEvaluationTemplateById", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

    @Override
	public List<ProEvaluationTemplate> listProEvaluationTemplatesByConditions(HashMap<String, Object> hashMap) {
		List<ProEvaluationTemplate> proEvaluationTemplates = null;
		try {
			proEvaluationTemplates=sqlSession.selectList("selectProEvaluationTemplatesByConditions", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluationTemplates ;
	}

}
