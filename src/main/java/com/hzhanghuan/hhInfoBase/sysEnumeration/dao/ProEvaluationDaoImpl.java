package com.hzhanghuan.hhSurvey.evaluation.dao;

import hanghuan.product.evaluation.entity.ProEvaluation;
import hanghuan.product.evaluation.entity.ProEvaluationRecordInfo;
import hanghuan.product.evaluation.entity.ProEvaluationResult;
import org.mybatis.spring.SqlSessionTemplate;
import org.qsjy.commons.base.dao.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测评功能模块实现
 */
@Repository
public class ProEvaluationDaoImpl extends BaseDaoImpl<ProEvaluation> implements ProEvaluationDao{
	@Resource
	private SqlSessionTemplate  sqlSession;

    @Override
	public ProEvaluation findProEvaluationById(Long id) {
		ProEvaluation proEvaluation = null;
		try {
			proEvaluation = (ProEvaluation) sqlSession.selectOne("findProEvaluationById", id); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluation;
	}

    @Override
	public ProEvaluation findProEvaluationByName(String name) {
		ProEvaluation proEvaluation = null;
		try {
			proEvaluation = sqlSession.selectOne("findProEvaluationByName", name);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return proEvaluation;
	}
/*
* 复制测评记录
*
* user_to_evaluation没复制
* */
	@Override
	public  Long copyProEvaluationById(Long id) {
		ProEvaluation proEvaluation = null;
		Long evaluationId=null;
		try {
			proEvaluation = (ProEvaluation) sqlSession.selectOne("findProEvaluationById", id);
			proEvaluation.setStatusId(0L);
			sqlSession.insert("insertProEvaluation", proEvaluation);
			evaluationId=proEvaluation.getId();
			List<Map<String,Object>> list=sqlSession.selectList("selectUserToProEvaluationByProEvaluationId",id);
			for(Map<String,Object> map:list){
				map.put("evaluationId",evaluationId);
				sqlSession.insert("insertUserToProEvaluation", map);

			}
			evaluationId=proEvaluation.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return evaluationId;

		}


	}

	@Override
	public List<ProEvaluation> findProEvaluationsByLabelId(HashMap<String, Object> hashMap) {
		return sqlSession.selectList("selectProEvaluationsByLabelId",hashMap);
	}


	@Override
	public void addProEvaluation(ProEvaluation proEvaluation) {
		try {
			sqlSession.insert("insertProEvaluation", proEvaluation);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void insertLabelToEvaluation(Long labelId) {
		try {
			sqlSession.insert("insertLabelToProEvaluation", labelId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateProEvaluation(HashMap<String, Object> hashMap) {
		try {
			sqlSession.update("updateProEvaluationById", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateLabelToEvaluationByEvalationId(HashMap<String, Object> hashMap) {
		try {
			sqlSession.update("updateLabelToProEvaluationByEvalationId", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProEvaluation(Long id) {
		try {
			sqlSession.delete("deleteProEvaluationById", id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteLabelToEvaluationById(Long evaluationId) {
		try {
			sqlSession.delete("deleteLabelToEvaluationById", evaluationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUserToProEvaluationByEvaluationId(Long evaluationId) {
		try {
			sqlSession.delete("deleteUserToProEvaluationByEvaluationId", evaluationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String selectCommentsByUserId(HashMap<String, Object> hashMap) {
		String rs = "";
		List<String> comments = sqlSession.selectList("selectCommentsByUserId",hashMap);
		if(comments!=null && comments.size()>0){
			for(String comment:comments){
				rs = rs + comment;
			}
		}
		return rs;
	}

	@Override
	public List<ProEvaluation> listProEvaluations(HashMap<String, Object> hashMap) {
		List<ProEvaluation> proEvaluations = null;
		try {
			proEvaluations=sqlSession.selectList("selectAllProEvaluationsByConditions", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluations ;
	}

    @Override
	public List<ProEvaluation> listProEvaluationsByPage(HashMap<String, Object> hashMap) {
		List<ProEvaluation> proEvaluations = null;
		try {
			proEvaluations = sqlSession.selectList("selectAllProEvaluationsByConditions", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluations;
	}

	@Override
	public List<ProEvaluation> getApprovalApplyListByConditions(HashMap<String,Object> hashMap){
		List<ProEvaluation> proEvaluations = null;
		try {
			proEvaluations = sqlSession.selectList("selectApprovalApplyListByConditions", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluations;
	}




	@Override
	public List<ProEvaluation> listProEvaluationsByConditions(
			HashMap<String, Object> hashMap) {
		List<ProEvaluation> proEvaluations = null;
		try {
			proEvaluations=sqlSession.selectList("selectAllProEvaluationsByConditions", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluations;
	}

	@Override
	public void addUserToEvaluation(HashMap<String, Object> hashMap) {
		try {
			sqlSession.insert("insertUserToProEvaluation", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ProEvaluation> listProEvaluationsByUserIdByPage(
			HashMap<String, Object> hashMap) {
		List<ProEvaluation> proEvaluations = null;
		try {
			proEvaluations=sqlSession.selectList("selectProEvaluationsByUserIdByPage", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluations;
	}

	@Override
	public List<ProEvaluationResult> listProEvaluationResultsByEvaluationId(
			HashMap<String, Object> hashMap) {
		List<ProEvaluationResult> proEvaluationResults = null;
		try {
			proEvaluationResults=sqlSession.selectList("selectGroupedProEvaluationResultsByConditions", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return proEvaluationResults;
	}

	@Override
	public Integer countProEvaluationsByConditions(
			HashMap<String, Object> hashMap) {
		Integer count = null;
		try {
			count=sqlSession.selectOne("countProEvaluationsByConditions", hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count ;
	}

	@Override
	public void deleteUsersByEvaluationId(Long id) {
		sqlSession.update("deleteUsersByProEvaluationId", id);
	}

	@Override
	public List<ProEvaluationRecordInfo> selectRecordsFinishedWithUser(HashMap<String, Object> hashMap) {
		return sqlSession.selectList("selectRecordsFinishedWithUser",hashMap);
	}

//	@Override
//	public List<String> selectCommentByUserId(HashMap<String, Object> hashMap) {
//		return sqlSession.selectList("selectProEvaluationCommentByUserId",hashMap);
//	}

}
