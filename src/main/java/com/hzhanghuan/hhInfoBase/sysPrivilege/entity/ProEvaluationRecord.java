package com.hzhanghuan.hhSurvey.evaluation.entity;

import hanghuan.jixiaoqs.entity.InfoDepartment;
import hanghuan.jixiaoqs.entity.SysUser;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 测评结果记录类
 * Created by chengduo on 2016/11/5.
 */
public class ProEvaluationRecord {
    private Long id;
    private Long evaluatorCommonId;
    private Long evaluatedUserCommonId;
    private Long evaluationId;
    private Long rankId;
    private String rankMultiselectId;
    private Date createTime;
    private Date updateTime;
    private Boolean finished;
    private BigDecimal weightStr;

    private String comment;

    private SysUser evaluatedUser;
    private InfoDepartment evaluatedDepartment;

    private SysUser evaluator;
    private ProEvaluation evaluation;


    public BigDecimal getWeightStr() {
        return weightStr;
    }

    public void setWeightStr(BigDecimal weightStr) {
        this.weightStr = weightStr;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvaluatorCommonId() {
        return evaluatorCommonId;
    }

    public void setEvaluatorCommonId(Long evaluatorCommonId) {
        this.evaluatorCommonId = evaluatorCommonId;
    }

    public Long getEvaluatedUserCommonId() {
        return evaluatedUserCommonId;
    }

    public void setEvaluatedUserCommonId(Long evaluatedUserCommonId) {
        this.evaluatedUserCommonId = evaluatedUserCommonId;
    }

    public Long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(Long evaluationId) {
        this.evaluationId = evaluationId;
    }

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public String getRankMultiselectId() {
        return rankMultiselectId;
    }

    public void setRankMultiselectId(String rankMultiselectId) {
        this.rankMultiselectId = rankMultiselectId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public SysUser getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(SysUser evaluator) {
        this.evaluator = evaluator;
    }

    public ProEvaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(ProEvaluation evaluation) {
        this.evaluation = evaluation;
    }

}
