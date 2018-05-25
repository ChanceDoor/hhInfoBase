package com.hzhanghuan.hhSurvey.evaluation.biz;

import hanghuan.product.evaluation.dao.ProEvaluationRecordDao;
import hanghuan.product.evaluation.entity.ProEvaluationRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * 测评记录业务实现类
 * @author chengduo
 * @version 1.0.8 2016-11-05
 */
@Service
@Transactional
public class ProEvaluationRecordBizImpl implements ProEvaluationRecordBiz{
	@Resource
	private ProEvaluationRecordDao proEvaluationRecordDao;

	@Override
	public ProEvaluationRecord findProEvaluationRecordById(Long id) {
		
			return proEvaluationRecordDao.findProEvaluationRecordById(id);
	
	}

	@Override
	public ProEvaluationRecord findProEvaluationRecordByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProEvaluationRecord(ProEvaluationRecord proEvaluationRecord) {
		try {
			proEvaluationRecordDao.addProEvaluationRecord(proEvaluationRecord);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteProEvaluationRecord(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateProEvaluationRecord(ProEvaluationRecord proEvaluationRecord) {
		try {
			proEvaluationRecordDao.updateProEvaluationRecord(proEvaluationRecord);
	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ProEvaluationRecord> listProEvaluationRecordsByConditions(
			HashMap<String, Object> hashMap) {
		return proEvaluationRecordDao.listProEvaluationRecordsByConditions(hashMap);
	}

	@Override
	public List<ProEvaluationRecord> listProEvaluationRecordsByPage(
			HashMap<String, Object> hashMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer countProEvaluationRecordsByConditions(
			HashMap<String, Object> hashMap) {
		
		return proEvaluationRecordDao.countProEvaluationRecordsByConditions(hashMap);
	}

	@Override
	public ProEvaluationRecord findProEvaluationRecordByConditions(HashMap<String, Object> hashMap) {
		return proEvaluationRecordDao.findProEvaluationRecordByConditions(hashMap);
	}

	@Override
	public List<HashMap<Object, Object>> getSimpleResult(HashMap<String, Object> hashMap) {
		return proEvaluationRecordDao.getSimpleResult(hashMap);
	}

	@Override
	public BigDecimal findSysRoleWeightBySysUser(Long id) throws Exception{
		return proEvaluationRecordDao.findSysRoleWeight(id);
	}


}

