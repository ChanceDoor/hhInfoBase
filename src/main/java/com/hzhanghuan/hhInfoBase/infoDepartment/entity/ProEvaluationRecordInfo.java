package com.hzhanghuan.hhSurvey.evaluation.entity;

import hanghuan.jixiaoqs.entity.SysUser;

/**
 * 测评结果记录类
 * Created by chengduo on 2016/11/5.
 */
public class ProEvaluationRecordInfo {
    private Long userId;
    private Integer amount;
    private SysUser user;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }
}
