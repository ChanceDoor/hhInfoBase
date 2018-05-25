package com.hzhanghuan.hhSurvey.evaluation.entity;

import hanghuan.jixiaoqs.entity.InfoDepartment;
import hanghuan.jixiaoqs.entity.SysUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 测评结果类
 */
public class ProEvaluationResult {
	private Long evaluatedUserCommonId;
	private SysUser evaluatedUser;
	private InfoDepartment evaluatedDepartment;
	private Integer[] voteCounts;
	private BigDecimal score;
	private BigDecimal[] roleWeights;
	
	private List<String> comment = new ArrayList<>();
	private String serialComment;

	public BigDecimal[] getRoleWeights() {
		return roleWeights;
	}

	public void setRoleWeights(BigDecimal[] roleWeights) {
		this.roleWeights = roleWeights;
	}

	public String getSerialComment() {
		return serialComment;
	}

	public void setSerialComment(String serialComment) {
		this.serialComment = serialComment;
	}

	public List<String> getComment() {
		return comment;
	}

	public void setComment(List<String> comment) {
		this.comment = comment;
	}



	public Long getEvaluatedUserCommonId() {
		return evaluatedUserCommonId;
	}

	public void setEvaluatedUserCommonId(Long evaluatedUserCommonId) {
		this.evaluatedUserCommonId = evaluatedUserCommonId;
	}

	public SysUser getEvaluatedUser() {
		return evaluatedUser;
	}

	public void setEvaluatedUser(SysUser evaluatedUser) {
		this.evaluatedUser = evaluatedUser;
	}

	public InfoDepartment getEvaluatedDepartment() {
		return evaluatedDepartment;
	}

	public void setEvaluatedDepartment(InfoDepartment evaluatedDepartment) {
		this.evaluatedDepartment = evaluatedDepartment;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}


	public Integer[] getVoteCounts() {
		return voteCounts;
	}

	public void setVoteCounts(Integer[] voteCounts) {
		this.voteCounts = voteCounts;
	}

}
