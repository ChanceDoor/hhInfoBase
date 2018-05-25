package com.hzhanghuan.hhSurvey.evaluation.action;

import hanghuan.jixiaoqs.biz.InfoDepartmentBiz;
import hanghuan.jixiaoqs.biz.SysEnumerationBiz;
import hanghuan.jixiaoqs.biz.SysUserBiz;
import hanghuan.jixiaoqs.entity.InfoDepartment;
import hanghuan.jixiaoqs.entity.SysEnumeration;
import hanghuan.jixiaoqs.entity.SysUser;
import hanghuan.jixiaoqs.util.ExportExcel;
import hanghuan.jixiaoqs.util.OrderUtil;
import hanghuan.product.evaluation.biz.ProEvaluationBiz;
import hanghuan.product.evaluation.biz.ProEvaluationRecordBiz;
import hanghuan.product.evaluation.biz.ProEvaluationTemplateBiz;
import hanghuan.product.evaluation.entity.*;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.poi.hssf.usermodel.*;
import org.json.JSONObject;
import org.qsjy.commons.base.entity.PageBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 实绩考评测评功能
 * @version 1.2.0 by ChanceDoor@2017-12-30
 */
@Controller
public class ProEvaluationAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private SysUserBiz sysUserBiz;
	@Resource
	private InfoDepartmentBiz infoDepartmentBiz;
	@Resource
	private SysEnumerationBiz sysEnumerationBiz;

	private String actionName = "ProEvaluationAction";

	@Resource
	private ProEvaluationBiz proEvaluationBiz;
	@Resource
	private ProEvaluationTemplateBiz proEvaluationTemplateBiz;
	@Resource
	private ProEvaluationRecordBiz proEvaluationRecordBiz;

	/**
	 * 导出测评记录
	 * @return
	 */
	// TODO:liuxj 优化该方法，减少循环，优化相关sql，减少耗时和内存、CPU消耗
	@RequestMapping("proEvaluationResultExport.do")
	public String proEvaluationResultExport(HttpServletRequest request,HttpServletResponse response) {

		// title =request.getParameter("title");
		String id =request.getParameter("id");

		ProEvaluation proEvaluation = proEvaluationBiz.findProEvaluationById(Long.parseLong(id));

		List<ProEvaluationResult> resultList = getProEvaluationResultByEvauation(proEvaluation);
		LinkedHashMap<String,  HashMap<String,Object>> optionsHashMap= proEvaluation.getOptionHashMap();

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet(proEvaluation.getTitle());
		sheet.setDefaultRowHeight((short) 30);
		sheet.setDefaultColumnWidth(6);
		HSSFCellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont titleFont = workbook.createFont();
		titleFont.setFontHeightInPoints((short) 18);
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		titleFont.setFontName("宋体");
		titleStyle.setFont(titleFont);

		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style2.setWrapText(true);

		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
		style3.setWrapText(true);

		HSSFFont font2 = workbook.createFont();
		font2.setFontHeightInPoints((short) 11);
		font2.setFontName("宋体");
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		style2.setFont(font2);
		style3.setFont(font2);
		// 第一行标题
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(50);
		HSSFRichTextString text = new HSSFRichTextString(proEvaluation.getTitle());

		ExportExcel.addCell(sheet, 0, 0, 0, 15, text, titleStyle);

		// 第二行：发起者（0-7）、起止时间（8-15）（//TODO:待补充）
		HSSFRow row1 = sheet.createRow(1);
		row1.setHeightInPoints(30);
		HSSFRichTextString text1 = new HSSFRichTextString("发起者：" + proEvaluation.getCreator().getName());
		ExportExcel.addCell(sheet, 1, 1, 0, 7, text1, style2);

//		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy/MM/dd");
//		HSSFRichTextString text2 = new HSSFRichTextString("起止日期" + myFmt.format(proEvaluation.getCreateTime()) + "-"
//				+ myFmt.format(proEvaluation.getEndTime()));
//		ExportExcel.addCell(sheet, 1, 1, 8, 15, text2, style2);

		// 第三行：所属部门（0）、姓名（1）、选项

		HSSFRow row2 = sheet.createRow(2);
		row2.setHeightInPoints(30);
		HSSFRichTextString text5 = new HSSFRichTextString("所属部门");
		if (proEvaluation.getTypeId() == 9004) {
			text5 = new HSSFRichTextString("部门名称");
			ExportExcel.addCell(sheet, 2, 2, 0, 3, text5, style2);
		}
		ExportExcel.addCell(sheet, 2, 2, 0, 1, text5, style2);

		if (proEvaluation.getTypeId() != 9004) {
			HSSFRichTextString text6 = new HSSFRichTextString("姓名");
			ExportExcel.addCell(sheet, 2, 2, 2, 3, text6, style2);
		}
		Integer index = 0;
		Iterator iter = optionsHashMap.entrySet().iterator();
		while (iter.hasNext()) {
			index = index + 1;
			Map.Entry entry = (Map.Entry) iter.next();
			HSSFRichTextString text7 = new HSSFRichTextString(entry.getKey().toString());
			ExportExcel.addCell(sheet, 2, 2, 3 + index, 3 + index, text7, style2);
		}
		HSSFRichTextString text7 = new HSSFRichTextString("得分");
		ExportExcel.addCell(sheet, 2, 2, 4 + index, 4 + index, text7, style2);

		HSSFRichTextString text8 = new HSSFRichTextString("备注");
		ExportExcel.addCell(sheet, 2, 2, 5 + index, 9 + index, text8, style2);

		// 第四行开始：所属部门（0）、姓名（1）、选项

		for (int i = 0; i < resultList.size(); i++) {
			HSSFRow row3 = sheet.createRow(3 + i);
			row3.setHeightInPoints(30);
			System.out.println(resultList.get(i));
			HSSFRichTextString text9 = null;
			if (proEvaluation.getTypeId() == 9004) {
				text9 = new HSSFRichTextString(resultList.get(i).getEvaluatedDepartment().getName());
				ExportExcel.addCell(sheet, 3 + i, 3 + i, 0, 3, text9, style3);
			} else {
				try{
					//TODO:部门可能需要修改
					text9 = new HSSFRichTextString(resultList.get(i).getEvaluatedUser().getDepartments().get(0).getName());
				}catch (Exception e){
					text9 = new HSSFRichTextString("");
				}
				ExportExcel.addCell(sheet, 3 + i, 3 + i, 0, 1, text9, style3);

				text9 = new HSSFRichTextString(resultList.get(i).getEvaluatedUser().getName());
				ExportExcel.addCell(sheet, 3 + i, 3 + i, 2, 3, text9, style3);
			}

			index = 0;
			iter = optionsHashMap.entrySet().iterator();
			while (iter.hasNext()) {
				if(proEvaluation.getOpenWeight()==1){
					text9 = new HSSFRichTextString(resultList.get(i).getVoteCounts()[index].toString()+"/"+resultList.get(i).getRoleWeights()[index].toString());
				}else{
					text9 = new HSSFRichTextString(resultList.get(i).getVoteCounts()[index].toString());
				}
				ExportExcel.addCell(sheet, 3 + i, 3 + i, 4 + index, 4 + index, text9, style2);
				index = index + 1;
				iter.next();
			}
			text9 = new HSSFRichTextString(resultList.get(i).getScore().toString());
			ExportExcel.addCell(sheet, 3 + i, 3 + i, 4 + index, 4 + index, text9, style2);
			text9 = new HSSFRichTextString(resultList.get(i).getSerialComment());
			ExportExcel.addCell(sheet, 3 + i, 3 + i, 5 + index, 9 + index, text9, style3);

		}

		response.setCharacterEncoding("utf-8");
		response.setContentType("application/vnd.ms-excel");
		String fileName= proEvaluation.getTitle()+"测评结果.xls";
		try {
			String agent = request.getHeader("User-Agent");
			if(agent.toUpperCase().indexOf("MSIE")>0||agent.toUpperCase().indexOf("TRIDENT")>0||agent.toUpperCase().indexOf("EDGE")>0){
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}else{
				fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
			}
			response.setHeader("Content-Disposition","atachment;fileName="+fileName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			//InputStream inputStream = new ByteArrayInputStream(out.toByteArray());

			OutputStream os = response.getOutputStream();
			workbook.write(os);
			//out.flush();
			//out.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * 进行测评页面
	 * @return
	 */
	@RequestMapping("proEvaluationRecords.do")
	public String proEvaluationRecords(HttpServletRequest request,HttpSession session,@RequestParam(value = "type", defaultValue ="1") Integer type) {
		String idStr = request.getParameter("id");
		if(idStr==null){
			return "forward:myProEvaluations.do";
		}
		SysUser curUser=sysUserBiz.getCurUser();
		Long userId = curUser.getCommonId();
		ProEvaluation proEvaluation = proEvaluationBiz.findProEvaluationById(Long.parseLong(idStr));
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		JSONObject jsonObject = new JSONObject(proEvaluation.getData());
		String bodyJson = (String) jsonObject.get("body");
		bodyJson = bodyJson.substring(1, bodyJson.length() - 1);
		String[] a = bodyJson.split(",");
		int index = 0;
		LinkedHashMap<String,Object> optionsHashMap = new LinkedHashMap<String,Object>();
		for (int i = 0; i < a.length; i++) {
			index = index + 1;
			String[] b = a[i].split(":");
			optionsHashMap.put(b[0].substring(1, b[0].length() - 1), index);
		}

		if (type == 1) {
			hashMap.put("evaluatorId", curUser.getCommonId());
			hashMap.put("finished", 0);
			hashMap.put("available", 1);
			hashMap.put("evaluationId", idStr);
		} else if (type == 2) {
			hashMap.put("evaluatorId", curUser.getCommonId());
			hashMap.put("finished", 1);
			hashMap.put("evaluationId", idStr);
		} else if (type == 3) {
			hashMap.put("evaluatedUserId", curUser.getCommonId());
			hashMap.put("finished", 1);
			hashMap.put("evaluationId", idStr);
		}
		List<ProEvaluationRecord> evaluationsToDoList = proEvaluationRecordBiz.listProEvaluationRecordsByConditions(hashMap);

	/*查看部门为空的人员Id*/
	for(int i=0;i<evaluationsToDoList.size();i++){
			List<InfoDepartment> d=evaluationsToDoList.get(i).getEvaluatedUser().getDepartments();
			if(d==null||d.size()==0){
				System.out.println("rel_user_to_department表缺少数据,userId为"+evaluationsToDoList.get(i).getEvaluatedUser().getId());
			}
		}
		Integer bestCount = 0;
		Integer goodCount = 0;
		if (evaluationsToDoList != null && evaluationsToDoList.size() > 0) {
			proEvaluation = evaluationsToDoList.get(0).getEvaluation();
			hashMap.clear();
			hashMap.put("rankId", 505);
			hashMap.put("evaluationId", proEvaluation.getId());
			hashMap.put("evaluatorId", userId);
			bestCount = proEvaluationRecordBiz.countProEvaluationRecordsByConditions(hashMap);

			hashMap.put("rankId", 506);
			goodCount = proEvaluationRecordBiz.countProEvaluationRecordsByConditions(hashMap);

		}
		request.setAttribute("bestCount", bestCount);
		request.setAttribute("goodCount", goodCount);
		request.setAttribute("evaluationsToDoList",evaluationsToDoList);
		request.setAttribute("optionsHashMap",optionsHashMap);
		request.setAttribute("type",type);
		request.setAttribute("proEvaluation",proEvaluation);
		request.setAttribute("actionName","ProEvaluationAction");
		return "proEvaluations/index.jsp";
	}

	/**
	 * 测评预览页面
	 * @return
	 */
	@RequestMapping("proEvaluationsPreview.do")
	public String proEvaluationsPreview(HttpServletRequest request,HttpSession session,@RequestParam(value = "type", defaultValue ="1") Integer type) {
		String idStr = request.getParameter("id");
		if(idStr==null){
			return "forward:myProEvaluations.do";
		}
		ProEvaluation proEvaluation = proEvaluationBiz.findProEvaluationById(Long.parseLong(idStr));
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		JSONObject jsonObject = new JSONObject(proEvaluation.getData());
		String bodyJson = (String) jsonObject.get("body");
		bodyJson = bodyJson.substring(1, bodyJson.length() - 1);
		String[] a = bodyJson.split(",");
		int index = 0;
		LinkedHashMap<String,Object> optionsHashMap = new LinkedHashMap<String,Object>();
		for (int i = 0; i < a.length; i++) {
			index = index + 1;
			String[] b = a[i].split(":");
			optionsHashMap.put(b[0].substring(1, b[0].length() - 1), index);
		}

		if (type == 1) {
			hashMap.put("forPreview", 1);
			hashMap.put("evaluationId", idStr);
		}
		List<ProEvaluationRecord> evaluationsToDoList = proEvaluationRecordBiz.listProEvaluationRecordsByConditions(hashMap);

	/*查看部门为空的人员Id*/
		for(int i=0;i<evaluationsToDoList.size();i++){
			List<InfoDepartment> d=evaluationsToDoList.get(i).getEvaluatedUser().getDepartments();
			if(d==null||d.size()==0){
				System.out.println("rel_user_to_department表缺少数据,userId为"+evaluationsToDoList.get(i).getEvaluatedUser().getId());
			}
		}
		Integer bestCount = 0;
		Integer goodCount = 0;

		request.setAttribute("bestCount", bestCount);
		request.setAttribute("goodCount", goodCount);
		request.setAttribute("evaluationsToDoList",evaluationsToDoList);
		request.setAttribute("optionsHashMap",optionsHashMap);
		request.setAttribute("type",type);
		request.setAttribute("proEvaluation",proEvaluation);
		return "proEvaluations/preview.jsp";
	}

	/**
	 * 测评管理列表首页
	 * @return
	 */
	@RequestMapping("proEvaluations.do")
	public String index(HttpServletRequest request,HttpSession session,
						@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		actionName = "ProEvaluationAdminAction";
		String evaluationSearchKeyword =request.getParameter("evaluationSearchKeyword");
		String typeIdStr = request.getParameter("typeId");
		Long typeId=null;
		if(typeIdStr!=null&&typeIdStr!=""){
			typeId=Long.parseLong(typeIdStr);
		}

		Integer pageSize = 15;
		Integer totalCount = 0;
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("currentPage", pageNum);
		hashMap.put("pageSize", pageSize);
		hashMap.put("typeId", typeId);
		request.setAttribute("typeId", typeId);
		if (evaluationSearchKeyword != null && evaluationSearchKeyword != "") {
			// try {
			// evaluationSearchKeyword = new
			// String(evaluationSearchKeyword.getBytes("ISO-8859-1"),"UTF-8");
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }
			hashMap.put("evaluationSearchKeyword", evaluationSearchKeyword);
		}
		SysUser curUser = sysUserBiz.getCurUser();
		System.out.println(curUser);
		hashMap.put("creatorCommonId", curUser.getCommonId());

		PageBean<ProEvaluation> pageBean = proEvaluationBiz.getProEvaluationsByConditions(hashMap);

		hashMap.clear();
		//hashMap.put("description", "测评标签");
		//List<SysEnumeration> sysEnumerations=sysEnumerationBiz.listSysEnumerationsByConditions(hashMap);
		hashMap.put("9001", "个人测评");
		hashMap.put("9004", "部门测评");
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("sysEnumerations", hashMap);
		request.setAttribute("actionName",actionName);
		request.setAttribute("evaluationSearchKeyword",evaluationSearchKeyword);
		
		return "proEvaluations/adminIndex.jsp";
	}

	@RequestMapping("proEvaluationEdit.do")
	public String editProEvaluation(HttpServletRequest request,@RequestParam String id) {

		ProEvaluation proEvaluation= proEvaluationBiz.findProEvaluationById(Long.parseLong(id));
		if (proEvaluation != null && proEvaluation.getData() != null) {
			request.setAttribute("data", proEvaluation.getData());
		}

		// 根据evaluation获取测评人
		List<SysUser> userList = sysUserBiz.listReviewersByProEvaluationId(proEvaluation.getId());
		// 循环对每个测评人添加是否已完成所有测评状态：1 0
		for (SysUser user : userList) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("evaluationId", proEvaluation.getId());
			hashMap.put("evaluatorId", user.getId());
			hashMap.put("finished", 0);

			Integer unfinishedRecords = proEvaluationRecordBiz.countProEvaluationRecordsByConditions(hashMap);
			HashMap<String, Object> resultHashMap = new HashMap<String, Object>();
			if (unfinishedRecords > 0) {
				resultHashMap.put(String.valueOf(user.getId()), 0);
			} else {
				resultHashMap.put(String.valueOf(user.getId()), 1);
			}
		}
		Long userId = (Long) request.getSession().getAttribute("USER_ID");
		// 获取测评模板，只能获取全局模板和自己创建的模板：scope_id=7001 7004
		List<ProEvaluationTemplate> templateList = new ArrayList<ProEvaluationTemplate>();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("scopeId", 7001);
		templateList.addAll(proEvaluationTemplateBiz.listProEvaluationTemplatesByConditions(hashMap));

		hashMap.clear();
		hashMap.put("scopeId", 7004);
		hashMap.put("creatorId", userId);
		templateList.addAll(proEvaluationTemplateBiz.listProEvaluationTemplatesByConditions(hashMap));

		// 获取测评标签集合
		hashMap.clear();
		hashMap.put("description", "测评标签");
		List<SysEnumeration> labels = sysEnumerationBiz.listSysEnumerationsByConditions(hashMap);
		request.setAttribute("labels", labels);
		request.setAttribute("proEvaluation", proEvaluation);
		request.setAttribute("templateList", templateList);

		actionName = "ProEvaluationAdminAction";
		return "proEvaluations/edit.jsp";
	}

	/**
	 * 我的测评首页
	 * @return
	 */
	@RequestMapping("myProEvaluations.do")
	public String myProEvaluations(HttpServletRequest request,@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

		List<Integer> evaluationsToDoCount = new ArrayList<Integer>();
		SysUser curUser = sysUserBiz.getCurUser();
		Integer limit = 15;
		Integer start = (pageNum-1)*limit;

		String evaluationSearchKeyword = request.getParameter("evaluationSearchKeyword");
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("start", start);
		hashMap.put("end", limit + start);
		if (evaluationSearchKeyword != null && evaluationSearchKeyword != ""&&!evaluationSearchKeyword.equals("undefined")) {
			// try {
			// evaluationSearchKeyword = new
			// String(evaluationSearchKeyword.getBytes("ISO-8859-1"),"UTF-8");
			// } catch (UnsupportedEncodingException e) {
			// e.printStackTrace();
			// }
			hashMap.put("evaluationSearchKeyword", evaluationSearchKeyword);
		}
		hashMap.put("typeId2",20002);
		hashMap.put("userCommonId", curUser.getCommonId());
		hashMap.put("statusId", 1);


		Integer count = proEvaluationBiz.countProEvaluationsByConditions(hashMap);
		if(count==null){
			count=0;
		}

		Integer pageSize=15;
		List<ProEvaluation> evaluationList = proEvaluationBiz.listProEvaluationsByUserIdByPage(hashMap);
		hashMap.clear();
		// 获取测评的未完成条数
		if (evaluationList != null) {
			for (ProEvaluation evaluation : evaluationList) {
				hashMap.put("evaluationId", evaluation.getId());
				hashMap.put("evaluatorId", curUser.getCommonId());
				hashMap.put("finished", 0);
				hashMap.put("statusId", 1);
				Integer toDoCount = proEvaluationRecordBiz.countProEvaluationRecordsByConditions(hashMap);
				evaluationsToDoCount.add(toDoCount);

			}
		}
		hashMap.clear();
		hashMap.put("9001", "个人测评");
		hashMap.put("9004", "部门测评");
		PageBean<ProEvaluation> pageBean = new PageBean<ProEvaluation>(pageNum, pageSize, count, evaluationList);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("evaluationSearchKeyword", evaluationSearchKeyword);
		request.setAttribute("typeMap",hashMap);
		request.setAttribute("evaluationsToDoCount",evaluationsToDoCount);
		request.setAttribute("actionName","ProEvaluationAction");
		return "proEvaluations/myIndex.jsp";
	}

	/**
	 * 获取测评预览界面
	 * @param request
	 * @return
	 */
	@RequestMapping("myProEvaluationsShortCut.do")
	@ResponseBody
	public String myProEvaluationsShortCut(HttpServletRequest request) {
		String idStr=request.getParameter("id");
		ProEvaluation proEvaluation = proEvaluationBiz.findProEvaluationById(Long.parseLong(idStr));
		String data=proEvaluation.getDescription();
		if(data==null)
			data="空";
		String jsonString="{\"success\":true,\"data\":\""+data+"\"}";
		return jsonString;
	}
	/**
	 * 查看测评结果
	 * @return
	 */
	// TODO:liuxj 优化该方法，减少循环，优化相关sql，减少耗时和内存、CPU消耗
	@RequestMapping("proEvaluationAdminShow.do")
	public String adminShow(HttpServletRequest request) {
		String idStr =request.getParameter("id");

		ProEvaluation proEvaluation = proEvaluationBiz.findProEvaluationById(Long.parseLong(idStr));
		List<ProEvaluationResult> resultList = getProEvaluationResultByEvauation(proEvaluation);
		request.setAttribute("resultList",resultList);
		request.setAttribute("proEvaluation",proEvaluation);
		request.setAttribute("optionsHashMap",proEvaluation.getOptionHashMap());
		request.setAttribute("actionName","ProEvaluationAdminAction");
		return "proEvaluations/adminShow.jsp";
	}

	/**
	 * 查看测评情况
	 * @return
	 */
	@RequestMapping("proEvaluationEvaluatorsShow.do")
	public String evaluatorsShow(HttpServletRequest request) {
		String idStr =request.getParameter("id");

		ProEvaluation proEvaluation = proEvaluationBiz.findProEvaluationById(Long.parseLong(idStr));


		HashMap<Object, Object> resultHashMap = new HashMap<Object, Object>();

		if (proEvaluation.getEvaluationRecords() == null || proEvaluation.getEvaluationRecords().size() == 0) {
			// 还没启用
		}else{
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("evaluationId", proEvaluation.getId());
			hashMap.put("finished", 0);
			//获取改测评的测评情况
			List<ProEvaluationRecordInfo> evaluationRecordsFinishedWithUser = proEvaluationBiz.selectRecordsFinishedWithUser(hashMap);
			int completeCount=0;
			for(int i=0;i<evaluationRecordsFinishedWithUser.size();i++){
				if(evaluationRecordsFinishedWithUser.get(i).getAmount()<=0){
					completeCount++;
				}
			}
			request.setAttribute("evaluationRecordsFinishedWithUser",evaluationRecordsFinishedWithUser);
			request.setAttribute("completeCount",completeCount);

		}

		request.setAttribute("proEvaluation",proEvaluation);
		request.setAttribute("actionName","ProEvaluationAdminAction");
		return "proEvaluations/evaluatorsShow.jsp";
	}

	/**
	 * 复制测评记录
	 * @return
	 */
	@RequestMapping("copyProEvaluationById.do")
	public String copyProEvaluationById(HttpServletRequest request) {
		String idStr =request.getParameter("id");
		proEvaluationBiz.copyProEvaluationById(Long.parseLong(idStr));
		return "redirect:proEvaluations.do";
	}
//

	/**
	 * 创建测评界面
	 * @return
	 */
	@RequestMapping("proEvaluationNew.do")
	public String proEvaluationNew(HttpServletRequest request,HttpSession session) {

		SysUser curUser = sysUserBiz.getCurUser();
		System.out.println("curUser"+curUser);

		// 获取测评模板，只能获取全局模板和自己创建的模板：scope_id=7001 7004
		List<ProEvaluationTemplate> templateList = new ArrayList<ProEvaluationTemplate>();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("scopeId", 7001);
		templateList.addAll(proEvaluationTemplateBiz.listProEvaluationTemplatesByConditions(hashMap));

		hashMap.clear();
		hashMap.put("scopeId", 7004);
		hashMap.put("creatorCommonId", curUser.getCommonId());
		templateList.addAll(proEvaluationTemplateBiz.listProEvaluationTemplatesByConditions(hashMap));

		// 获取测评标签集合
		hashMap.clear();
		hashMap.put("description", "测评标签");
		List<SysEnumeration> labels = sysEnumerationBiz.listSysEnumerationsByConditions(hashMap);
		request.setAttribute("labels", labels);
		request.setAttribute("templateList",templateList);
		request.setAttribute("actionName","ProEvaluationAdminAction");
		return "proEvaluations/new.jsp";
	}

	/**
	 * 启动测评
	 * @return
	 */
	@RequestMapping("proEvaluationToggle.do")
	public String toggleStatus(HttpServletRequest request) {
		String idStr =request.getParameter("id");
		ProEvaluationThread proEvaluationThread = new ProEvaluationThread();
		proEvaluationThread.setIdStr(idStr);
		proEvaluationThread.setProEvaluationBiz(proEvaluationBiz);
		proEvaluationThread.setProEvaluationRecordBiz(proEvaluationRecordBiz);

		proEvaluationThread.start();

		request.setAttribute("actionName","ProEvaluationAdminAction");
		return "redirect:proEvaluations.do";
	}

	/**
	 * 删除测评
	 * @return
	 */
	@RequestMapping("proEvaluationDelete.do")
	public String delete(HttpServletRequest request) {
		String idStr =request.getParameter("id");

		ProEvaluation proEvaluation = proEvaluationBiz.findProEvaluationById(Long.parseLong(idStr));
		if (proEvaluation != null) {
			try {
				proEvaluationBiz.deleteProEvaluation(proEvaluation.getId());
				proEvaluationBiz.deleteUserToProEvaluationByEvaluationId(proEvaluation.getId());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("actionName","ProEvaluationAdminAction");
		return "redirect:proEvaluations.do";
	}

	public HashMap getrankMultiselectIdList (String[] rankIds){
//		String[] rankIds=new String[]{"1-1001","2-1001","3-1001","1-1002","2-1002","3-1002","5-1002","3-1003"};
		List<String> recordIds=new ArrayList<>();

		for(int i=0;i<rankIds.length;i++){
			Boolean a=true;
			String recordId=rankIds[i].split("-")[1];
			for (String s:recordIds){
				if (s.equals(recordId)){
					a=false;
					break;
				}
			}
			if (a){
				recordIds.add(recordId);
			}

		}
		List<String> mulList=new ArrayList<>();
		for (String s:recordIds){
			String  muls="";

			for(int i=0;i<rankIds.length;i++){

				String recordId=rankIds[i].split("-")[1];
				if (s.equals(recordId)){
					if(muls==""){
						if(!rankIds[i].split("-")[0].equals("0")){
							muls=rankIds[i].split("-")[0];
						}


					}else{
						if(!rankIds[i].split("-")[0].equals("0")){
							muls=muls+","+rankIds[i].split("-")[0];
						}


					}
				}

			}
			mulList.add(muls);
		}
		HashMap hashMap=new HashMap();
		hashMap.put("id",recordIds);
		hashMap.put("rankMultiselectIdList",mulList);

		return hashMap;


	}


	/**
	 * 更新测评记录
	 * @return
	 */
	@RequestMapping("updateProEvaluationRecordAjax.do")
	@ResponseBody
	public String updateProEvaluationRecordAjax(HttpServletRequest request) {
		String[] rankIds =request.getParameterValues("rankIds[]");
		String[] comment =request.getParameterValues("textArea[]");
		String multiselect =request.getParameter("multiselect");
		Integer succeeFlag = 0;

			HashMap hashMap=	getrankMultiselectIdList(rankIds);
		List<String> idList= (List<String>) hashMap.get("id");
		List<String> rankMultiselectIdList= (List<String>) hashMap.get("rankMultiselectIdList");

		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		if (rankIds != null) {
			for (int i = 0; i < comment.length; i++) {
				ProEvaluationRecord proEvaluationRecord=null;
				String recordId="";
				if(multiselect.equals("0")){
					String rankId = rankIds[i].split("-")[0];
					//pro_evaluation_record
					 recordId = rankIds[i].split("-")[1];
					proEvaluationRecord= proEvaluationRecordBiz
							.findProEvaluationRecordById(Long.parseLong(recordId));
					proEvaluationRecord.setRankId(Long.parseLong(rankId));
					proEvaluationRecord.setFinished(true);
					proEvaluationRecord.setComment(comment[i]);
				}else{
					String rankId = rankIds[i].split("-")[0];
					//pro_evaluation_record
					recordId = rankIds[i].split("-")[1];


					proEvaluationRecord= proEvaluationRecordBiz
							.findProEvaluationRecordById(Long.parseLong(idList.get(i)));
					proEvaluationRecord.setRankId(0L);

					proEvaluationRecord.setRankMultiselectId(rankMultiselectIdList.get(i));
					proEvaluationRecord.setFinished(true);
					proEvaluationRecord.setComment(comment[i]);
				}





//				System.out.println(comment[i]);
				try {
					BigDecimal weightStr = proEvaluationRecordBiz.findSysRoleWeightBySysUser(Long.parseLong(idList.get(i)));
					if(weightStr!=null) {
						proEvaluationRecord.setWeightStr(weightStr);
					}else{
						succeeFlag=2;
						String jsonString = "{\"success\":" + succeeFlag + "}";
						return jsonString;
					}
					proEvaluationRecordBiz.updateProEvaluationRecord(proEvaluationRecord);
				} catch (Exception e) {
					e.printStackTrace();
						String jsonString = "{\"success\":" + succeeFlag + "}";
						return jsonString;
				}
			}

		}
		succeeFlag = 1;
		String jsonString = "{\"success\":" + succeeFlag + "}";
		return jsonString;
	}


	/**
	 * 创建测评
	 * @return
	 */
	@RequestMapping("createProEvaluationAjax.do")
	@ResponseBody
	public String createEvaluationAjax(HttpServletRequest request) throws ParseException {
		String[] userIds = request.getParameterValues("userIds[]");
		String[] reviewerIds = request.getParameterValues("reviewerIds[]");
		String title = request.getParameter("title");
		String labelId = request.getParameter("labelId");
		String typeIdStr = request.getParameter("typeId");
		String data = request.getParameter("data");
		String templateId = request.getParameter("templateId");
		String openWeight = request.getParameter("openWeight");
		String orderType = request.getParameter("orderType");
		String multiselect = request.getParameter("multiselect");
		String description=request.getParameter("description");

		String forTimeSelect=request.getParameter("forTimeSelect");
		String forTime="";
		if(forTimeSelect.equals("8003")){
			String year=request.getParameter("year");
			String month=request.getParameter("month");
			forTime=year+"-"+month+"-01";
		}
		else if(forTimeSelect.equals("8004")){
			String year=request.getParameter("year");
			String season=request.getParameter("season");
			forTime=year+"-"+season+"-01";
		}
		else if(forTimeSelect.equals("8005")){
			String year=request.getParameter("year");
			forTime=year+"-"+"01"+"-01";
		}
		SysUser curUser = sysUserBiz.getCurUser();

		// 先插入evaluation
		ProEvaluation evaluation = new ProEvaluation();
		evaluation.setTitle(title);
		evaluation.setData(data);
		if (templateId != null && !templateId.trim().equalsIgnoreCase("")) {
			evaluation.setTemplateId(Long.parseLong(templateId));
		}
		evaluation.setCreatorCommonId(curUser.getCommonId());

		if (typeIdStr != null) {
			evaluation.setTypeId(Long.parseLong(typeIdStr));
		} else {
			evaluation.setTypeId((long) 9001);
		}
		if (openWeight != null&&openWeight != "") {
			evaluation.setOpenWeight(Long.parseLong(openWeight));
		} else {
			evaluation.setOpenWeight(0L);
		}
		if (multiselect != null&&multiselect != "") {
			evaluation.setMultiselect(Long.parseLong(multiselect));
		} else {
			evaluation.setMultiselect(0L);
		}

		if (orderType != null&&orderType != "") {
			evaluation.setOrderType(Long.parseLong(orderType));
		} else {
			evaluation.setOrderType(0L);
		}

		evaluation.setStatusId(0L);
		evaluation.setForTimeSelect(Long.parseLong(forTimeSelect));
		evaluation.setDescription(description);
		if(!forTime.equals("")){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			evaluation.setForTime(sdf.parse(forTime));
		}
		Integer succeeFlag = 0;
		try {
			proEvaluationBiz.addProEvaluation(evaluation);

			// 生成测评纪录
			System.out.println("自主测评集已生成，名称：" + evaluation.getTitle() + "，id：" + evaluation.getId());

			for (String reviewerId : reviewerIds) {
				// 添加评测者与评测的关联
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				SysUser reviewer = sysUserBiz.findSysUserById(Long.parseLong(reviewerId));
				hashMap.put("userCommonId", reviewer.getCommonId());
				hashMap.put("evaluationId", evaluation.getId());
				hashMap.put("typeId", 20002);
				proEvaluationBiz.addUserToEvaluation(hashMap);
			}

			for (String userId : userIds) {
				if(evaluation.getTypeId()==9001){
					SysUser evaluatedUser = sysUserBiz.findSysUserById(Long.parseLong(userId));

					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("userCommonId", evaluatedUser.getCommonId());
					hashMap.put("evaluationId", evaluation.getId());
					hashMap.put("typeId", 20001);
					proEvaluationBiz.addUserToEvaluation(hashMap);
				}else{
					InfoDepartment evaluatedDepartment = infoDepartmentBiz.findInfoDepartmentById(Long.parseLong(userId));
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("userCommonId", evaluatedDepartment.getId());
					hashMap.put("evaluationId", evaluation.getId());
					hashMap.put("typeId", 20001);
					proEvaluationBiz.addUserToEvaluation(hashMap);
				}
				// 添加被评者与评测的关联


			}
			succeeFlag = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(labelId!=null&&labelId!=""&&succeeFlag==1){
			proEvaluationBiz.insertLabelToEvaluation(Long.parseLong(labelId));
		}
		JsonConfig config = new JsonConfig();
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		String jsonString = "{\"success\":" + succeeFlag + ",\"data\":{\"id\":" + 1 + "}}";
		return jsonString;
	}

	/**
	 * 修改测评
	 * @return
	 */
	@RequestMapping("updateProEvaluationAjax.do")
	@ResponseBody
	public String updateProEvaluationAjax(HttpServletRequest request) {
		String id = request.getParameter("id");
		String[] userIds =request.getParameterValues("userIds[]");
		String[] reviewerIds =request.getParameterValues("reviewerIds[]");
		String title =request.getParameter("title");
		String labelId =request.getParameter("labelId");
		String typeIdStr =request.getParameter("typeId");
		String data =request.getParameter("data");
		String templateId =request.getParameter("templateId");
		String openWeight =request.getParameter("openWeight");
		String orderType =request.getParameter("orderType");
		String multiselect =request.getParameter("multiselect");

		String description=request.getParameter("description");

		String forTimeSelect=request.getParameter("forTimeSelect");
		String forTime="";
		if(forTimeSelect.equals("8003")){
			String year=request.getParameter("year");
			String month=request.getParameter("month");
			forTime=year+"-"+month+"-01";
		}
		else if(forTimeSelect.equals("8004")){
			String year=request.getParameter("year");
			String season=request.getParameter("season");
			forTime=year+"-"+season+"-01";
		}
		else if(forTimeSelect.equals("8005")){
			String year=request.getParameter("year");
			forTime=year+"-"+"01"+"-01";
		}
		SysUser curUser =sysUserBiz.getCurUser();
		String jsonString;
		try {

			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			// 先插入evaluation
			ProEvaluation evaluation = proEvaluationBiz.findProEvaluationById(Long.parseLong(id));
			evaluation.setTitle(title);
			evaluation.setForTime(sdf.parse(forTime));
			evaluation.setForTimeSelect(Long.parseLong(forTimeSelect));
			evaluation.setDescription(description);
			evaluation.setData(data);
			if (templateId != null && !templateId.trim().equalsIgnoreCase("")) {
				evaluation.setTemplateId(Long.parseLong(templateId));
			}


			if (openWeight != null&&openWeight != "") {
				evaluation.setOpenWeight(Long.parseLong(openWeight));
			} else {
				evaluation.setOpenWeight(0L);
			}
			if (multiselect != null&&multiselect != "") {
				evaluation.setMultiselect(Long.parseLong(multiselect));
			} else {
				evaluation.setMultiselect(0L);
			}

			if (orderType != null&&orderType != "") {
				evaluation.setOrderType(Long.parseLong(orderType));
			} else {
				evaluation.setOrderType(0L);
			}

			evaluation.setCreatorCommonId(curUser.getCommonId());

			if (typeIdStr != null) {
				evaluation.setTypeId(Long.parseLong(typeIdStr));
			} else {
				evaluation.setTypeId((long) 9001);
			}

			evaluation.setStatusId(0L);

			// 删除原有的关联
			proEvaluationBiz.deleteUsersByEvaluationId(evaluation.getId());

			// 更新
			HashMap<String,Object> params = new HashMap<String,Object>();
			params.put("id", evaluation.getId());
			params.put("title", evaluation.getTitle());
			params.put("data", evaluation.getData());
			params.put("templateId", evaluation.getTemplateId());
			params.put("creatorCommonId", evaluation.getCreatorCommonId());
			params.put("typeId", evaluation.getTypeId());
			params.put("statusId", evaluation.getStatusId());
			params.put("openWeight", evaluation.getOpenWeight());
			params.put("multiselect", evaluation.getMultiselect());
			params.put("description", evaluation.getDescription());
			params.put("forTime", evaluation.getForTime());
			params.put("forTimeSelect", evaluation.getForTimeSelect());

			proEvaluationBiz.updateProEvaluation(params);
			if(labelId!=null&&labelId!=""){
				params.clear();
				params.put("evaluationId", Long.parseLong(id));
				params.put("labelId", labelId);
				proEvaluationBiz.updateLabelToEvaluationByEvalationId(params);
			}
			// 生成测评纪录
			System.out.println("自主测评集已修改，名称：" + evaluation.getTitle() + "，id：" + evaluation.getId());

			for (String reviewerId : reviewerIds) {
				// 添加评测者与评测的关联
				SysUser reviewer = sysUserBiz.findSysUserById(Long.parseLong(reviewerId));
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				hashMap.put("userCommonId", reviewer.getCommonId());
				hashMap.put("evaluationId", evaluation.getId());
				hashMap.put("typeId", 20002);
				proEvaluationBiz.addUserToEvaluation(hashMap);
			}

			for (String userId : userIds) {
				if(evaluation.getTypeId()==9001){
					SysUser evaluatedUser = sysUserBiz.findSysUserById(Long.parseLong(userId));

					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("userCommonId", evaluatedUser.getCommonId());
					hashMap.put("evaluationId", evaluation.getId());
					hashMap.put("typeId", 20001);
					proEvaluationBiz.addUserToEvaluation(hashMap);
				}else{
					InfoDepartment evaluatedDepartment = infoDepartmentBiz.findInfoDepartmentById(Long.parseLong(userId));
					HashMap<String, Object> hashMap = new HashMap<String, Object>();
					hashMap.put("userCommonId", evaluatedDepartment.getId());
					hashMap.put("evaluationId", evaluation.getId());
					hashMap.put("typeId", 20001);
					proEvaluationBiz.addUserToEvaluation(hashMap);
				}
			}

			JsonConfig config = new JsonConfig();
			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

			jsonString = "{\"success\":" + 1 + ",\"data\":{\"id\":" + 1 + "}}";
		} catch (Exception e) {
			jsonString = "{\"success\":" + 0 + ",\"data\":{\"id\":" + 1 + "}}";
		}
		return jsonString;
	}





//
//	public String tempSyncEvaluationRecords() {
//		// 获取所有类型为个人的测评
//		List<ProEvaluation> evaluations = proEvaluationBiz.listProEvaluationsByConditions(null);
//		// 循环获取每个测评的userIds和reviewerIds
//		for (ProEvaluation evaluation : evaluations) {
//
//			List<SysUser> users = sysUserBiz.listUsersByEvaluationId(evaluation.getId());
//			List<SysUser> reviewers = sysUserBiz.listReviewersByEvaluationId(evaluation.getId());
//			for (SysUser user : users) {
//
//				// 对每个userIds检查是否存在测评条目，若缺失则创建
//
//				for (SysUser reviewer : reviewers) {
//					HashMap<String, Object> hashMap = new HashMap<String, Object>();
//					hashMap.put("evaluationId", evaluation.getId());
//					hashMap.put("evaluatorId", reviewer.getId());
//					hashMap.put("evaluatedUserId", user.getId());
//
//					ProEvaluationRecord er = proEvaluationRecordBiz.findProEvaluationRecordByConditions(hashMap);
//					if (er == null) {
//						ProEvaluationRecord proEvaluationRecord = new ProEvaluationRecord();
//						proEvaluationRecord.setEvaluatorId(reviewer.getId());
//						proEvaluationRecord.setEvaluatedUserId(user.getId());
//						proEvaluationRecord.setEvaluationId(evaluation.getId());
//
//						proEvaluationRecordBiz.addProEvaluationRecord(proEvaluationRecord);
//					}
//
//				}
//			}
//		}
//
//		return SUCCESS;
//	}

	/**
	 * 根据evaluation获取测评结果
	 */
	private List<ProEvaluationResult> getProEvaluationResultByEvauation(ProEvaluation proEvaluation){
		// 根据evaluation获取测评结果
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("evaluationId", proEvaluation.getId());
		LinkedHashMap<String,  HashMap<String,Object>> optionHashMap = proEvaluation.getOptionHashMap();
		hashMap.put("options",optionHashMap);
		hashMap.put("openWeight",proEvaluation.getOpenWeight());
		hashMap.put("multiselect",proEvaluation.getMultiselect());
		List<HashMap<Object,Object>> rsHashMap = proEvaluationRecordBiz.getSimpleResult(hashMap);
		List<ProEvaluationResult> resultList = new ArrayList<ProEvaluationResult>();
		if(rsHashMap!=null){
			for (int i=0;i<rsHashMap.size();i++){
				ProEvaluationResult tempResult = new ProEvaluationResult();
				HashMap<Object,Object> obj = rsHashMap.get(i);
				String  objectIdStr =obj.get("user_id").toString();
				/*String  objectStrId = obj.get("id").toString();*/
				Long objectId = Long.parseLong(objectIdStr);
				if(proEvaluation.getTypeId()==9004){
					InfoDepartment evaluatedDepartment = infoDepartmentBiz.findInfoDepartmentById(objectId);
					tempResult.setEvaluatedDepartment(evaluatedDepartment);
				}else{
					SysUser evaluatedUser = sysUserBiz.findSysUserByCommonId(objectId);
					tempResult.setEvaluatedUser(evaluatedUser);
				}
				if(obj.get("comment")!=null){
					String comment = obj.get("comment").toString();
					Integer len = comment.length();
					if(len>1){
						tempResult.setSerialComment(comment.substring(1,len));
					}
				}
				Integer[] counts = new Integer[optionHashMap.size()];
				BigDecimal[] roleWeights = new BigDecimal[optionHashMap.size()];
				// 对该result设置对应option的票数数组
				Integer index = 0;
				Iterator iter =optionHashMap.entrySet().iterator();
				BigDecimal[] weights = new BigDecimal[optionHashMap.size()];
				while (iter.hasNext()) {
					Integer num=0;
					Map.Entry<String,Object> enter = (Map.Entry<String,Object>)iter.next();
					String key = enter.getKey();
					if(obj.get(key)==null){
						obj.put(key,0);
					}
					Integer count = new BigDecimal(obj.get(key).toString()).intValue();
					Object obj2 = obj.get(key);
					BigDecimal weight = new BigDecimal(((HashMap<String,Object>)enter.getValue()).get("weight").toString());
					BigDecimal roleWeight = new BigDecimal(0);
					if(obj2!=null){
						roleWeight = new BigDecimal(obj.get(key).toString());
					}
					counts[index]=count;
					roleWeights[index]=roleWeight;
					weights[index] =weight;
					index = index + 1;
				}
				tempResult.setVoteCounts(counts);
				tempResult.setRoleWeights(roleWeights);
				BigDecimal score = BigDecimal.ZERO;
				for (int j = 0; j < counts.length; j++) {
					score = weights[j].multiply(roleWeights[j]).add(score);
				}
				tempResult.setScore(score);

				resultList.add(tempResult);
			}
		}
		resultList.sort(OrderUtil.<ProEvaluationResult>compare()
				.thenComparing((p, o) -> o.getScore().compareTo(p.getScore())));
		return resultList;
	}

	/**
	 * 批量上传候选人
	 *
	 * @param excel
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "importEvaluationMembersExcel.do", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String importEvaluationMembersExcel(@RequestParam(value = "excle", required = false) MultipartFile excel) throws IOException {
		int num = 0;
		String excelFileName = excel.getOriginalFilename();
		List<HashMap<String, Object>> successList = new ArrayList<HashMap<String, Object>>(); // 可以直接插入的数据
		List<HashMap<String, Object>> errorList = new ArrayList<HashMap<String, Object>>(); // 错误的行数和信息

		if (excelFileName.toUpperCase().endsWith(".XLS")) {
			if (excelFileName == null //
					|| excel == null //
					|| !excelFileName.toUpperCase().endsWith(".XLS")) {
				throw new RuntimeException("请上传正确的Excle文件！");
			}
		} else if (excelFileName.toUpperCase().endsWith(".XLSX")) {
			if (excelFileName == null //
					|| excel == null //
					|| !excelFileName.toUpperCase().endsWith(".XLSX")) {
				throw new RuntimeException("请上传正确的Excle文件！");
			}
		}

		// 解析Excle文件
		InputStream fi = null;
		try {
			fi = excel.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 创建一个工作簿
		HSSFWorkbook wb = new HSSFWorkbook(fi);
		// 得到第一个工作表
		HSSFSheet sheet = wb.getSheetAt(0);

		HSSFRow row = null;
		HSSFCell cell = null;

		int rowNum = sheet.getLastRowNum();
		for (int i = 0; i <= rowNum; i++) {
			try {

				num++;
				row = sheet.getRow(i);

				String name; // 姓名
				cell = row.getCell(0);
				if (cell != null) {
					cell.setCellType(cell.CELL_TYPE_STRING);
					name = cell.getStringCellValue();
					// 去掉空格
					name = name.replaceAll(" ","");
				} else {
					throw new Exception("姓名不能为空！");
				}


				List<SysUser> user1 = new ArrayList<SysUser>();

				// name
				if (name != null && !"".equals(name.trim())) {
				} else {
					throw new Exception("-姓名-不能为空！");
				}
				HashMap<String,Object> hashMap = new HashMap<String,Object>();
				hashMap.put("name",name);
				user1 = sysUserBiz.listSysUsersByConditions(hashMap);
				if (user1 == null) {
					throw new Exception("没有找到此用户请检查姓名是否正确：" + name);
				}

				HashMap<String, Object> successMap = new HashMap<String, Object>();
				successMap.put("name", name);
				successMap.put("id", user1.get(0).getId());
				successList.add(successMap);
			} catch (Exception e) {
				HashMap<String, Object> errorMap = new HashMap<String, Object>();
				errorMap.put("row", String.valueOf(num + 1));
				errorMap.put("reason", e.toString());
				errorList.add(errorMap);
				System.out.println("在第" + (num + 1) + "行导入失败;" + "excel编号为：" + (num + 1));
				continue;
			}

		}

		JSONArray errorJson = JSONArray.fromObject(errorList); // 出错的数据存入数组
		JSONArray successJson = JSONArray.fromObject(successList); // 成功保存的题目数组
		String jsonString = "{\"errorJson\":" + errorJson.toString() + ",\"successJson\":" + successJson.toString() + "}";

		// principlesOfApplication = successList;
		return jsonString;
	}
	@RequestMapping(value="importEvaluationNameQuery.do", produces = {"text/html;charset=UTF-8"})
	@ResponseBody
	public String importEvaluationNameQuery(@RequestParam(required = false) String userName) {
		//声明返回值
		List<SysUser> user = new ArrayList<SysUser>();
		//声明hashmap，
		HashMap<String,Object> hashMap = new HashMap<String,Object>();
		List<String> successList = new ArrayList<String>();
		// 先排除空格
		String str=userName.replaceAll(" ", "");
		 str=str.replaceAll("\r|\n|\\s", " ");

		String[] strs=str.split(" ");

		for(int a=0;a<strs.length;a++) {
			hashMap.put("name", strs[a].toString());
			user = sysUserBiz.listSysUsersByConditions(hashMap);
			if (user.size() > 1) {
				String uerName = user.get(0).getName();
				successList.add(uerName);
			} else {
				for (int i = 0; i < user.size(); i++) {
					long id = user.get(i).getId();
					String uerid = String.valueOf(id);
					String uerName = user.get(i).getName();
					long unitId = user.get(i).getUnitId();
					String uniId = String.valueOf(unitId);
					successList.add(uerid);
				}
			}
		}
		JSONArray successJson = JSONArray.fromObject(successList); // 成功保存的题目数组
		String jsonString = successJson.toString();
		return jsonString;
	}
}
