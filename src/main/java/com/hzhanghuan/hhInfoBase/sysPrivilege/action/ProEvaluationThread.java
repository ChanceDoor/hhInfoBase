package com.hzhanghuan.hhSurvey.evaluation.action;

import hanghuan.jixiaoqs.entity.InfoDepartment;
import hanghuan.jixiaoqs.entity.SysUser;
import hanghuan.product.evaluation.biz.ProEvaluationBiz;
import hanghuan.product.evaluation.biz.ProEvaluationRecordBiz;
import hanghuan.product.evaluation.entity.ProEvaluation;
import hanghuan.product.evaluation.entity.ProEvaluationRecord;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * Created by fire on 2017/12/13.
 */
public class ProEvaluationThread extends Thread {
    @Resource
    private ProEvaluationBiz proEvaluationBiz;
    @Resource
    private ProEvaluationRecordBiz proEvaluationRecordBiz;

    private String  idStr ;




    public String getIdStr() {
        return idStr;
    }

    public void setIdStr(String idStr) {
        this.idStr = idStr;
    }

    public ProEvaluationBiz getProEvaluationBiz() {
        return proEvaluationBiz;
    }

    public void setProEvaluationBiz(ProEvaluationBiz proEvaluationBiz) {
        this.proEvaluationBiz = proEvaluationBiz;
    }

    public ProEvaluationRecordBiz getProEvaluationRecordBiz() {
        return proEvaluationRecordBiz;
    }

    public void setProEvaluationRecordBiz(ProEvaluationRecordBiz proEvaluationRecordBiz) {
        this.proEvaluationRecordBiz = proEvaluationRecordBiz;
    }
/*
* 启动测评
* */
    public void run(){

        ProEvaluation proEvaluation = proEvaluationBiz.findProEvaluationById(Long.parseLong(idStr));
        Long oldStatusId = proEvaluation.getStatusId();
        if (proEvaluation != null) {
            //根据该测评最初的状态给出相应的状态
            if(proEvaluation.getStatusId()==0||proEvaluation.getStatusId()==-1){
                proEvaluation.setStatusId(1l);
            }else if(proEvaluation.getStatusId()==1){
                proEvaluation.setStatusId(-1l);
            }

            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("id", proEvaluation.getId());
            hashMap.put("statusId", proEvaluation.getStatusId());
            try {
                proEvaluationBiz.updateProEvaluation(hashMap);
            } catch (Exception e) {
                e.printStackTrace();
            }


            // 判断是否需要生成测评record
            if(oldStatusId==0 && proEvaluation.getStatusId()==1){
                // 首次启用生成测评条目
                System.out.println("首次启用生成测评条目");
                System.out.println("开始生成测评条目……");

                if(proEvaluation.getTypeId()==9001){
                    for (int i=0;i< proEvaluation.getToEvalUsers().size();i++) {
                        SysUser user=proEvaluation.getToEvalUsers().get(i);
                        System.out.println("对" + user.getId() + "生成测评条目……");
                        //
                        ProEvaluationRecord proEvaluationRecord = new ProEvaluationRecord();
                        proEvaluationRecord.setEvaluatedUserCommonId(user.getId());
                        proEvaluationRecord.setEvaluationId(proEvaluation.getId());
                        HashMap<String, Object> hashMap1=new HashMap<String, Object>();
                        hashMap1.put("evaluationId",proEvaluation.getId());
                        hashMap1.put("evaluatedUserId",user.getId());
                        for (int j=0;j<proEvaluation.getEvalUsers().size();j++) {
                            SysUser reviewer = proEvaluation.getEvalUsers().get(j);
                            proEvaluationRecord.setEvaluatorCommonId(reviewer.getId());
                            hashMap1.put("evaluatorId", reviewer.getId());
                            if(proEvaluationRecordBiz.findProEvaluationRecordByConditions(hashMap1)==null)
                                proEvaluationRecordBiz.addProEvaluationRecord(proEvaluationRecord);
                        }
                        System.out.println("对" + user.getId() + "生成测评条目完毕");
                    }
                }else if(proEvaluation.getTypeId()==9004){
                    for (int i=0;i<proEvaluation.getToEvalDepartments().size();i++) {
                        InfoDepartment dep = proEvaluation.getToEvalDepartments().get(i);
                        System.out.println("对" + dep.getId() + "生成测评条目……");
                        //
                        ProEvaluationRecord proEvaluationRecord = new ProEvaluationRecord();
                        proEvaluationRecord.setEvaluatedUserCommonId(dep.getId());
                        proEvaluationRecord.setEvaluationId(proEvaluation.getId());
                        HashMap<String, Object> hashMap1=new HashMap<String, Object>();
                        hashMap1.put("evaluationId", proEvaluation.getId());
                        hashMap1.put("evaluatedUserId", dep.getId());
                        for (int j=0;j<proEvaluation.getEvalUsers().size();j++) {
                            SysUser reviewer = proEvaluation.getEvalUsers().get(j);
                            proEvaluationRecord.setEvaluatorCommonId(reviewer.getId());
                            hashMap1.put("evaluatorId", reviewer.getId());
                            if(proEvaluationRecordBiz.findProEvaluationRecordByConditions(hashMap1)==null)
                                proEvaluationRecordBiz.addProEvaluationRecord(proEvaluationRecord);
                        }
                        System.out.println("对" + dep.getId() + "生成测评条目完毕");
                    }
                }


                System.out.println("自主生成测评完毕");
            }



        }

    }





}

