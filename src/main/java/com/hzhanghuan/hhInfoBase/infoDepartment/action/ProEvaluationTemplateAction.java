package com.hzhanghuan.hhSurvey.evaluation.action;

import hanghuan.jixiaoqs.biz.SysEnumerationBiz;
import hanghuan.jixiaoqs.biz.SysUserBiz;
import hanghuan.jixiaoqs.entity.SysUser;
import hanghuan.product.evaluation.biz.ProEvaluationTemplateBiz;
import hanghuan.product.evaluation.entity.ProEvaluationTemplate;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 测评模板控制器
 */
@Controller
public class ProEvaluationTemplateAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private SysUserBiz sysUserBiz;
	@Resource
	private SysEnumerationBiz sysEnumerationBiz;
	private String actionName="ProEvaluationAction";
	@Resource
	private ProEvaluationTemplateBiz proEvaluationTemplateBiz;

	/**
	 * 根据id查找测评模板
	 * @return
	 */
	@RequestMapping(value="findProEvaluationTemplateAjax.do",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String findProEvaluationTemplateAjax(HttpServletRequest request){
		String idStr = request.getParameter("id");
		int succeeFlag = 0;
		String data = "";
		ProEvaluationTemplate template = null;
		if(idStr!=null){
			template = proEvaluationTemplateBiz.findProEvaluationTemplateById(Long.parseLong(idStr));
			if(template!=null){
				succeeFlag = 1;
			}
		}
		JsonConfig config = new JsonConfig();   
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		config.setExcludes(new String[]{"creator"});
        JSONArray array = JSONArray.fromObject(template,config);
		String jsonString = "{\"success\":"+succeeFlag+",\"data\":"+array.toString()+"}";
		return jsonString;
	}
	/**
	 * 根据id删除测评模板
	 * @return
	 */
	@RequestMapping(value="deleteProEvaluationTemplateAjax.do",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String deleteProEvaluationTemplateAjax(HttpServletRequest request){
		String idStr = request.getParameter("id");
		int succeeFlag = 0;
		String data = "";
		int template = 0;
		// TODO:暂时不允许删除全局模板
		if(idStr!=null){
			Long tid = Long.parseLong(idStr);
			ProEvaluationTemplate tp = proEvaluationTemplateBiz.findProEvaluationTemplateById(tid);
			if(tp!=null){
				if(tp.getScopeId()==7001L){
					succeeFlag = 0;
				}else{
					proEvaluationTemplateBiz.deleteProEvaluationTemplate(tid);
					succeeFlag = 1;
				}
			}

		}

		String jsonString = "{\"success\":"+succeeFlag+"}";
		return jsonString;
	}
	/**
	 * 创建测评模板
	 * @return
	 */
	@RequestMapping(value="createProEvaluationTemplateAjax.do",produces={"application/json;charset=UTF-8"})
	@ResponseBody
	public String createProEvaluationTemplateAjax(HttpServletRequest request){
		String options = request.getParameter("options");
		String name = request.getParameter("name");
		org.json.JSONArray jsonArray = new org.json.JSONArray(options); 
		
		// 遍历 arrayList,拼装{key:value}
		String body = "{";
		for(int i=0;i<jsonArray.length();i++){
			if(i>0){
				body = body+",";
			}
			JSONObject iObj = jsonArray.getJSONObject(i);
			body = body+"\""+iObj.get("key").toString()+"\":"+iObj.get("value").toString();
		}
		body = body+"}";
		
		SysUser curUser = sysUserBiz.getCurUser();
		ProEvaluationTemplate template = new ProEvaluationTemplate();
		template.setCreatorCommonId(curUser.getCommonId());
		template.setBody(body);
		template.setName(name);
		template.setScopeId((long)7004);
		
		
		Integer succeeFlag=0;
		try{
			proEvaluationTemplateBiz.addProEvaluationTemplate(template);
			succeeFlag=1;
		}catch(Exception e){
			e.printStackTrace();
		}	
		
		
		JsonConfig config = new JsonConfig();   
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);    
        JSONArray array = JSONArray.fromObject(template,config);
		String jsonString = "{\"success\":"+succeeFlag+",\"data\":"+array.toString()+"}";
		return jsonString;
	}
}
