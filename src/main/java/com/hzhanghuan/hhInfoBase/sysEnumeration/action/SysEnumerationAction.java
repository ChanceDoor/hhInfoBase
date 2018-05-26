package com.hzhanghuan.hhInfoBase.sysEnumeration.action;

import com.hzhanghuan.hhCommonBase.entity.PageBean;
import com.hzhanghuan.hhInfoBase.sysEnumeration.biz.SysEnumerationBiz;
import com.hzhanghuan.hhInfoBase.sysEnumeration.entity.SysEnumeration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * 数据字典入口
 * @author ChanceDoor<chengduo@hzhanghaun.com>
 * @version 1.0.1
 *
 */
@Controller
public class SysEnumerationAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private SysEnumerationBiz sysEnumerationBiz;

	private int pageSize=15;
	private String actionName="SysEnumerationAction";

	/* index */
	@RequestMapping("sysEnumerationsIndex.do")
	public String index(HttpServletRequest request,String enumerationKeyword,
			@RequestParam(value="pageNum",defaultValue="1")Integer pageNum){
		HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
		hashMap.put("currentPage", (pageNum));
		hashMap.put("pageSize",pageSize);
		if(enumerationKeyword!=null && enumerationKeyword!=""){
			hashMap.put("enumerationKeyword", enumerationKeyword);
		}
		int count = sysEnumerationBiz.countSysEnumerations(hashMap);
		PageBean<SysEnumeration> pageBean = sysEnumerationBiz.getSysEnumerationsPageBeanByConditions(hashMap);
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("actionName","SysEnumerationAction");
		return "/sysEnumerations/index.jsp";
	}
	
	//回显参数
	public void echoParameter(HttpServletRequest request){
		String pageNumStr = request.getParameter("pageNum");
		String enumerationKeyword = request.getParameter("enumerationKeyword");
		if(pageNumStr!=null&&!pageNumStr.equals("")){
			request.setAttribute("pageNum", Integer.parseInt(pageNumStr));
		}
		if(enumerationKeyword!=null){
			request.setAttribute("enumerationKeyword", enumerationKeyword);
		}
	}
	
	//创建一个form
	@RequestMapping("sysEnumerationsNew.do")
	public String newForm(HttpServletRequest request){
		//回显
		echoParameter(request);
		return "/sysEnumerations/new.jsp";
	}
	
	//创建数据字典中的一条记录
	@RequestMapping("sysEnumerationsCreate.do")
	public String create(HttpServletRequest request){
		String id = request.getParameter("id");
		String value = request.getParameter("value");
		String description = request.getParameter("description");
		
		SysEnumeration SysEnumeration = new SysEnumeration();
		SysEnumeration.setId(Long.parseLong(id));
		SysEnumeration.setValue(value);
		SysEnumeration.setDescription(description);
		Boolean success =false;
		String message;
		try{
			sysEnumerationBiz.addSysEnumeration(SysEnumeration);
			success = true;
			return "redirect:sysEnumerationsIndex.do";
		}catch(Exception e){
			message = ("添加失败：" + e.toString());
			request.setAttribute("message",message);
		}
		request.setAttribute("success",success);
		return "redirect:sysEnumerationsIndex.do";
	}
	
	
	
	//回显数据字典表示某一条数据
	@RequestMapping("sysEnumerationsEdit.do")
	public String edit(HttpServletRequest request){
		String id = request.getParameter("id");
		SysEnumeration sysEnumeration = sysEnumerationBiz.findSysEnumerationById(Long.valueOf(id));
		request.setAttribute("sysEnumeration", sysEnumeration);
		//回显
		echoParameter(request);
		return "/sysEnumerations/edit.jsp";
	}
	
	//更新记录
	@RequestMapping("sysEnumerationsUpdate.do")
	public String update(HttpServletRequest request){
		String id =request.getParameter("id");
		String value =request.getParameter("value");
		String description = request.getParameter("description");
		SysEnumeration sysEnumeration = sysEnumerationBiz.findSysEnumerationById(Long.valueOf(id));
		sysEnumeration.setValue(value);
		sysEnumeration.setDescription(description);
		Boolean success =false;
		String message;
		try{
			sysEnumerationBiz.updateSysEnumeration(sysEnumeration);
			success = true;
			return "redirect:sysEnumerationsIndex.do";
		}catch(Exception e){
			success = false;
			message = ("修改失败：" + e.toString());
			request.setAttribute("message",message);
		}
		request.setAttribute("success",success);
		return "redirect:sysEnumerationsIndex.do";
	}
	
	//删除某一条记录
	@RequestMapping("sysEnumerationsDelete.do")
	public String delete(HttpServletRequest request){
		String id =request.getParameter("id");
		SysEnumeration sysEnumeration = sysEnumerationBiz.findSysEnumerationById(Long.valueOf(id));
		Boolean success =false;
		String message;
		try{
			sysEnumerationBiz.delSysEnumeration(sysEnumeration);
			success = true;
			return "redirect:sysEnumerationsIndex.do";
		}catch(Exception e){
			success = false;
			message = ("删除失败" + e.toString());
			request.setAttribute("message",message);
		}
		request.setAttribute("success",success);
		return "redirect:sysEnumerationsIndex.do";
	}
}