package com.hzhanghuan.hhSurvey.evaluation.entity;

import hanghuan.jixiaoqs.entity.SysEnumeration;
import hanghuan.jixiaoqs.entity.SysUser;

/**
 * 测评模板类
 * Created by chengduo on 2016/11/5.
 */
public class ProEvaluationTemplate {
    private Long id;
    private String body;


    private Long creatorCommonId;
    private Long scopeId;
    private String name;

    private SysEnumeration scope;
    private SysUser creator;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getCreatorCommonId() {
        return creatorCommonId;
    }

    public void setCreatorCommonId(Long creatorCommonId) {
        this.creatorCommonId = creatorCommonId;
    }

    public Long getScopeId() {
        return scopeId;
    }

    public void setScopeId(Long scopeId) {
        this.scopeId = scopeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SysEnumeration getScope() {
        return scope;
    }

    public void setScope(SysEnumeration scope) {
        this.scope = scope;
    }

    public SysUser getCreator() {
        return creator;
    }

    public void setCreator(SysUser creator) {
        this.creator = creator;
    }

}
