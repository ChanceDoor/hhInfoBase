package com.hzhanghuan.hhSurvey.evaluation.biz;

import hanghuan.product.evaluation.dao.ProEvaluationDao;
import hanghuan.product.evaluation.entity.ProEvaluation;
import hanghuan.product.evaluation.entity.ProEvaluationRecordInfo;
import hanghuan.product.evaluation.entity.ProEvaluationResult;
import org.qsjy.commons.base.entity.PageBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ProEvaluationBizImpl implements ProEvaluationBiz{
	@Resource
	private ProEvaluationDao proEvaluationDao;


	@Override
	public ProEvaluation findProEvaluationById(Long id) {
		return proEvaluationDao.findProEvaluationById(id);
	}


	@Override
	public ProEvaluation findProEvaluationByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void copyProEvaluationById(Long id) {
		 proEvaluationDao.copyProEvaluationById(id);
	}

	@Override
	public List<ProEvaluation> findProEvaluationsByLabelId(HashMap<String, Object> hashMap) {
		return proEvaluationDao.findProEvaluationsByLabelId(hashMap);
	}


	@Override
	public void addProEvaluation(ProEvaluation proEvaluation) {
		try {
			proEvaluationDao.addProEvaluation(proEvaluation);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertLabelToEvaluation(Long labelId) {
		try {
			proEvaluationDao.insertLabelToEvaluation(labelId);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void deleteProEvaluation(Long id) {
		try {
			proEvaluationDao.deleteProEvaluation(id);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void deleteLabelToEvaluationById(Long evaluationId) {
		try {
			proEvaluationDao.deleteLabelToEvaluationById(evaluationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteUserToProEvaluationByEvaluationId(Long evaluationId) {
		try {
			proEvaluationDao.deleteUserToProEvaluationByEvaluationId(evaluationId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void updateProEvaluation(HashMap<String, Object> hashMap) {
		try {
			proEvaluationDao.updateProEvaluation(hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public void updateLabelToEvaluationByEvalationId(HashMap<String, Object> hashMap) {
		try {
			proEvaluationDao.updateLabelToEvaluationByEvalationId(hashMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String selectCommentsByUserId(HashMap<String, Object> hashMap) {
		return proEvaluationDao.selectCommentsByUserId(hashMap);
	}


	@Override
	public List<ProEvaluation> listProEvaluations(
			HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<ProEvaluation> listProEvaluationsByPage(
			HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProEvaluation> getApprovalApplyListByConditions(
			HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProEvaluation> listProEvaluationsByConditions(HashMap<String, Object> hashMap) {
		return proEvaluationDao.listProEvaluationsByConditions(hashMap);
	}

	@Override
	public void addUserToEvaluation(HashMap<String, Object> hashMap) {
		proEvaluationDao.addUserToEvaluation(hashMap);
	}

	@Override
	public List<ProEvaluation> listProEvaluationsByUserIdByPage(
			HashMap<String, Object> hashMap) {
		return proEvaluationDao.listProEvaluationsByUserIdByPage(hashMap);
	}

	@Override
	public List<ProEvaluationResult> listProEvaluationResultsByEvaluationId(
			HashMap<String, Object> hashMap) {
		return proEvaluationDao.listProEvaluationResultsByEvaluationId(hashMap);
	}

	@Override
	public Integer countProEvaluationsByConditions(
			HashMap<String, Object> hashMap) {
		
		return proEvaluationDao.countProEvaluationsByConditions(hashMap);
	}
	
	@Override
	public void deleteUsersByEvaluationId(Long id) {
		proEvaluationDao.deleteUsersByEvaluationId(id);
	}

	@Override
	public List<ProEvaluationRecordInfo> selectRecordsFinishedWithUser(HashMap<String, Object> hashMap) {
		return proEvaluationDao.selectRecordsFinishedWithUser(hashMap);
	}

	@Override
	public PageBean<ProEvaluation> getProEvaluationsByConditions(HashMap<Object, Object> hashMap) {
		return proEvaluationDao.getPageBean(hashMap);
	}

//	@Override
//	public List<String> selectCommentByUserId(HashMap<String, Object> hashMap) {
//		return proEvaluationDao.selectCommentByUserId(hashMap);
//	}

}

