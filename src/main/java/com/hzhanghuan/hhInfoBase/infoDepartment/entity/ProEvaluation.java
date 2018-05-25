package com.hzhanghuan.hhSurvey.evaluation.entity;

import hanghuan.jixiaoqs.entity.InfoDepartment;
import hanghuan.jixiaoqs.entity.SysEnumeration;
import hanghuan.jixiaoqs.entity.SysUser;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.*;

/**
 *
 * 测评类
 * @version 1.2.0 by ChanceDoor@2017-12-30
 */
public class ProEvaluation {
    private Long id;
    private String title;

    private List<SysUser> evalUsers = new ArrayList<SysUser>(); // 测评者
    private List<SysUser> toEvalUsers = new ArrayList<SysUser>(); // 被测评者(人员)
    private List<InfoDepartment> toEvalDepartments = new ArrayList<InfoDepartment>(); // 被测评者(部门)
    private Long typeId;
    private Date createTime;
    private Date startTime;
    private Date endTime;
    private Long creatorCommonId;
    private Long statusId;      // 测评状态: -1 关闭 0 未启用 1 启用中
    private Long templateId;
    private String data;
    private Long openWeight;
    private Long multiselect;

    private Long orderType; // 显示顺序类型：0：固定 1：随机
    public Long getOrderType() {
        return orderType;
    }

    public void setOrderType(Long orderType) {
        this.orderType = orderType;
    }


    private String description;//描述

    private SysEnumeration type;
    private ProEvaluationTemplate tmplate;
    private SysUser creator;
    private Date forTime;
    private long forTimeSelect;

    private List<ProEvaluationRecord> evaluationRecords = new ArrayList<ProEvaluationRecord>();
    private List<SysEnumeration> labels = new ArrayList<SysEnumeration>(); // 测评标签

    public Date getForTime() {
        return forTime;
    }

    public void setForTime(Date forTime) {
        this.forTime = forTime;
    }

    public long getForTimeSelect() {
        return forTimeSelect;
    }

    public void setForTimeSelect(long forTimeSelect) {
        this.forTimeSelect = forTimeSelect;
    }

    public String getDescription() {
//        if(description!=null&&!description.equals("")){
//            description=description.replace("\n","<br/>");
//            System.out.println(description);
//        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SysUser> getEvalUsers() {
        return evalUsers;
    }

    public void setEvalUsers(List<SysUser> evalUsers) {
        this.evalUsers = evalUsers;
    }

    public List<SysUser> getToEvalUsers() {
        return toEvalUsers;
    }

    public void setToEvalUsers(List<SysUser> toEvalUsers) {
        this.toEvalUsers = toEvalUsers;
    }

    public List<InfoDepartment> getToEvalDepartments() {
        return toEvalDepartments;
    }

    public void setToEvalDepartments(List<InfoDepartment> toEvalDepartments) {
        this.toEvalDepartments = toEvalDepartments;
    }

    public List<ProEvaluationRecord> getEvaluationRecords() {
        return evaluationRecords;
    }

    public void setEvaluationRecords(List<ProEvaluationRecord> evaluationRecords) {
        this.evaluationRecords = evaluationRecords;
    }

    public List<SysEnumeration> getLabels() {
        return labels;
    }

    public void setLabels(List<SysEnumeration> labels) {
        this.labels = labels;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getCreatorCommonId() {
        return creatorCommonId;
    }

    public void setCreatorCommonId(Long creatorCommonId) {
        this.creatorCommonId = creatorCommonId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getOpenWeight() {
        return openWeight;
    }

    public void setOpenWeight(Long openWeight) {
        this.openWeight = openWeight;
    }

    public Long getMultiselect() {
        return multiselect;
    }

    public void setMultiselect(Long multiselect) {
        this.multiselect = multiselect;
    }

    public SysEnumeration getType() {
        return type;
    }

    public void setType(SysEnumeration type) {
        this.type = type;
    }

    public ProEvaluationTemplate getTmplate() {
        return tmplate;
    }

    public void setTmplate(ProEvaluationTemplate tmplate) {
        this.tmplate = tmplate;
    }

    public SysUser getCreator() {
        return creator;
    }

    public void setCreator(SysUser creator) {
        this.creator = creator;
    }


    public LinkedHashMap<String,  HashMap<String,Object>> getOptionHashMap(){
        LinkedHashMap<String, HashMap<String,Object>> optionsHashMap = new LinkedHashMap<String,  HashMap<String,Object>>();


        // 先获取本测评所有选项
        JSONObject jsonObject = new JSONObject(this.getData());
        String bodyJson = (String) jsonObject.get("body");
        bodyJson = bodyJson.substring(1, bodyJson.length() - 1);
        String[] a = bodyJson.split(",");
        int index = 0;
        BigDecimal[] weights = new BigDecimal[a.length];
        for (int i = 0; i < a.length; i++) {
            String[] b = a[i].split(":");
            BigDecimal weight = new BigDecimal(b[1].trim());
            index = index + 1;
            HashMap<String,Object> temp = new  HashMap<String,Object>();
            temp.put("index",index);
            temp.put("weight",weight);
            optionsHashMap.put(b[0].substring(1, b[0].length() - 1), temp);
        }

        return optionsHashMap;
    }
}
