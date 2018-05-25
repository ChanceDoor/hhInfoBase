package hanghuan.jixiaoqs.entity;

import com.opensymphony.xwork2.ActionContext;
import hanghuan.jixiaoqs.biz.SysUserBiz;
import hanghuan.jixiaoqs.util.ApplicationUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SysUser {
	private Long id; // id
	private String username; // 用户名（目前手机号）
	private String password; // 密码
	private String name; // 姓名
	private String namePinYin; // 姓名
	private String showName; // 姓名
	private Long sexId; // 性别ID
	private Long identityId; // 身份证号
	private Date birthday; // 生日
	private Long educationId; // 学历ID
	private Long politicId; // 政治身份Id
	private String personalPhone; // 私人手机号
	private String workPhone; // 工作手机号
	private String policeNumber; // 警号
	private Long roleTypeId; // 系统角色
	private Long policeTypeId; // 暂时未用到
	private Long roleLevelId; // 现职级别
	private Long unitId; // 所属单位ID
	private String userUid; // 暂时未用到
	private Long accountId;
	private Long commonId;
	private Date updateStartTime;//更新开始时间
	private Date updateEndTime;//更新结束时间
	private Long updateVersion;//更新版本


	public String getToken() {
		return token;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private String token; // 用户令牌

	// 个人月度分数
	private List<HashMap<String, Object>> personalMonthScores;
	private String date;
	// 所属部门
	private List<InfoDepartment> departments;
	private InfoDepartment unit; // 所属单位ID

	private SysEnumeration sex; // 性别
	private SysEnumeration identity;
	private SysEnumeration education; // 学历
	private SysEnumeration politic; // 政治身份
	private List<SysRole> roles; // 角色
	private List<InfoJob> jobs; // 工作
	private List<InfoJob> mainJobs; // 主岗
	private List<InfoJob> viceJobs; // 副岗
	private List<UserHistory> userHistorys;
	
	private List<PfmcResult> beforeResults;//以前的Result

	private List<SimpleUser> virtualAccount;//虚拟账号

	private PfmcResult result; // 个人分值

	private SysEnumeration policeType;
	private SysEnumeration roleType;
	private SysEnumeration roleLevel;
	private SysEnumeration type; // 在职状态

	private String signature;
	private String signaturePath;

	private String avatar;
	private String avatarPath;
    
	private String description;
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUpdateVersion() {
		return updateVersion;
	}

	public void setUpdateVersion(Long updateVersion) {
		this.updateVersion = updateVersion;
	}

	public List<PfmcResult> getBeforeResults() {
		return beforeResults;
	}

	public void setBeforeResults(List<PfmcResult> beforeResults) {
		this.beforeResults = beforeResults;
	}

	public SysUser(long id) {
		this.id = id;
	}

	public SysUser() {
		super();
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamePinYin() {
		return namePinYin;
	}

	public void setNamePinYin(String namePinYin) {
		this.namePinYin = namePinYin;
	}

	public Long getSexId() {
		return sexId;
	}

	public void setSexId(Long sexId) {
		this.sexId = sexId;
	}

	public Long getIdentityId() {
		return identityId;
	}

	public void setIdentityId(Long identityId) {
		this.identityId = identityId;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public List<InfoJob> getJobs() {
		return jobs;
	}

	public void setJobs(List<InfoJob> jobs) {
		this.jobs = jobs;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Long getEducationId() {
		return educationId;
	}

	public void setEducationId(Long educationId) {
		this.educationId = educationId;
	}

	public String getPersonalPhone() {
		return personalPhone;
	}

	public void setPersonalPhone(String personalPhone) {
		this.personalPhone = personalPhone;
	}

	public Long getPoliticId() {
		return politicId;
	}

	public void setPoliticId(Long politicId) {
		this.politicId = politicId;
	}

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getPoliceNumber() {
		return policeNumber;
	}

	public void setPoliceNumber(String policeNumber) {
		this.policeNumber = policeNumber;
	}

	public SysEnumeration getSex() {
		return sex;
	}

	public void setSex(SysEnumeration sex) {
		this.sex = sex;
	}

	public SysEnumeration getIdentity() {
		return identity;
	}

	public void setIdentity(SysEnumeration identity) {
		this.identity = identity;
	}

	public SysEnumeration getEducation() {
		return education;
	}

	public void setEducation(SysEnumeration education) {
		this.education = education;
	}

	public SysEnumeration getPolitic() {
		return politic;
	}

	public void setPolitic(SysEnumeration politic) {
		this.politic = politic;
	}

	public List<InfoDepartment> getDepartments() {
		return departments;
	}

	public void setDepartments(List<InfoDepartment> departments) {
		this.departments = departments;
	}

	public SysEnumeration getRoleType() {
		return roleType;
	}

	public void setRoleType(SysEnumeration roleType) {
		this.roleType = roleType;
	}

	public SysEnumeration getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(SysEnumeration roleLevel) {
		this.roleLevel = roleLevel;
	}

	public Long getRoleTypeId() {
		return roleTypeId;
	}

	public void setRoleTypeId(Long roleTypeId) {
		this.roleTypeId = roleTypeId;
	}

	public Long getRoleLevelId() {
		return roleLevelId;
	}

	public void setRoleLevelId(Long roleLevelId) {
		this.roleLevelId = roleLevelId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}
	public Long getCommonId() {
		return commonId;
	}

	public void setCommonId(Long commonId) {
		this.commonId = commonId;
	}
	public Date getUpdateStartTime() {
		return updateStartTime;
	}

	public void setUpdateStartTime(Date updateStartTime) {
		this.updateStartTime = updateStartTime;
	}

	public Date getUpdateEndTime() {
		return updateEndTime;
	}

	public void setUpdateEndTime(Date updateEndTime) {
		this.updateEndTime = updateEndTime;
	}

	public List<SimpleUser> getVirtualAccount() {
		return virtualAccount;
	}

	public void setVirtualAccount(List<SimpleUser> virtualAccount) {
		this.virtualAccount = virtualAccount;
	}

	/***
	 * 根据字符串判断是否具有权限
	 * 
	 * @params String userId,String key
	 * @return boolean
	 */
	public boolean hasPrivilege(String key) {
		boolean rs = false;
		if (this.id == null) {
			return rs;
		}
		SysUserBiz sysUserBiz = (SysUserBiz) ApplicationUtil.getBean("sysUserBiz");

		List<SysRole> roles = sysUserBiz.findSysUserById(this.id).getRoles();

		if (roles != null) {
			for (SysRole sysRole : roles) {
				List<SysPrivilege> privilegeList = sysRole.getPrivileges();
				if (privilegeList != null) {
					for (SysPrivilege sysPrivilege : privilegeList) {
						if (sysPrivilege.getName().equalsIgnoreCase(key)) {
							rs = true;
							return rs;
						}
					}
				}
			}
		}
		return rs;
	}

	public String findSysUserNameById(Long id) {
		SysUserBiz sysUserBiz = (SysUserBiz) ApplicationUtil.getBean("sysUserBiz");
		 SysUser sysUser= sysUserBiz.findSysUserById(id);
		return sysUser.getName();
	}
	public List<SimpleUser> listVirtualNameByUserId(Long id) {
		SysUserBiz sysUserBiz = (SysUserBiz) ApplicationUtil.getBean("sysUserBiz");
		HashMap hashMap=new HashMap();
		hashMap.put("userId",id);
		List<SimpleUser> list= sysUserBiz.listVirtualNameByUserId(hashMap);
		return list;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignaturePath() {
		HttpServletRequest request = null;
		if(ActionContext.getContext()!=null){
			request = ServletActionContext.getRequest();
		}else{
			request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		}
		String site = request.getContextPath();
		int t = request.getServerPort();
		String host = request.getLocalAddr();
		String root = "http://" + host + ":" + t + site;
		signaturePath = root + signature;
		return signaturePath;
	}

	public void setSignaturePath(String signaturePath) {
		this.signaturePath = signaturePath;
	}

	public Long getPoliceTypeId() {
		return policeTypeId;
	}

	public void setPoliceTypeId(Long policeTypeId) {
		this.policeTypeId = policeTypeId;
	}

	public SysEnumeration getPoliceType() {
		return policeType;
	}

	public void setPoliceType(SysEnumeration policeType) {
		this.policeType = policeType;
	}

	public List<InfoJob> getMainJobs() {
		return mainJobs;
	}

	public void setMainJobs(List<InfoJob> mainJobs) {
		this.mainJobs = mainJobs;
	}

	public List<InfoJob> getViceJobs() {
		return viceJobs;
	}

	public void setViceJobs(List<InfoJob> viceJobs) {
		this.viceJobs = viceJobs;
	}

	public InfoDepartment getUnit() {
		return unit;
	}

	public void setUnit(InfoDepartment unit) {
		this.unit = unit;
	}

	public List<HashMap<String, Object>> getPersonalMonthScores() {
		return personalMonthScores;
	}

	public void setPersonalMonthScores(List<HashMap<String, Object>> personalMonthScores) {
		this.personalMonthScores = personalMonthScores;
	}

	public PfmcResult getResult() {
		return result;
	}

	public void setResult(PfmcResult result) {
		this.result = result;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAvatarPath() {
		HttpServletRequest request = null;
		if(ActionContext.getContext()!=null){
			request = ServletActionContext.getRequest();
		}else{
			request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		}
		String site = request.getContextPath();
		int t = request.getServerPort();
		String host = request.getLocalAddr();
		String root = "http://" + host + ":" + t + site;
		avatarPath = root + avatar;
		return avatarPath;
	}

	public void setAvatarPath(String avatarPath) {
		this.avatarPath = avatarPath;
	}

	public String getUserUid() {
		return userUid;
	}

	public void setUserUid(String userUid) {
		this.userUid = userUid;
	}

	/*
	 * 获取最高角色id,-1为无角色 排序：14>20>21>17>23>24>22>18>19>16>15
	 */
	public long getHighestRoleId() {
		long[] order = { (long) 14, (long) 20, (long) 21, (long) 17, (long) 23, (long) 24, (long) 22, (long) 18,
				(long) 19, (long) 16, (long) 15,(long) 27,(long) 28 };
		long rs = -1;
		if (this.getRoles() == null) {
			return -1;
		}
		Integer index = 10;
		for (SysRole role : this.getRoles()) {
			rs = role.getId();

			for (int i = 0; i < order.length; i++) {
				if (rs == order[i]) {
					index = i;
					break;
				}
			}

			for (SysRole r : this.getRoles()) {
				// 每次判断是否在order中存在,若存在,比较下标，保存更大者
				for (int i = 0; i < order.length; i++) {
					if (r.getId() == order[i]) {
						if (i < index) {
							index = i;
						}
					}
				}
			}
		}

		if (index >= 0) {
			rs = order[index];
		}
		return rs;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<UserHistory> getUserHistorys() {
		return userHistorys;
	}

	public void setUserHistorys(List<UserHistory> userHistorys) {
		this.userHistorys = userHistorys;
	}

	public SysEnumeration getType() {
		return type;
	}

	public void setType(SysEnumeration type) {
		this.type = type;
	}

	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * 获取当前使用的user记录
	 */
	public SysUser getCurrent(){
		SysUserBiz sysUserBiz = (SysUserBiz) ApplicationUtil.getBean("sysUserBiz");
		return sysUserBiz.findSysUserByCommonId(this.commonId);
	}


	/**
	 * 根据Date获取当时的用户
	 */
	public SysUser getUserByDate(Date date){
		SysUserBiz sysUserBiz = (SysUserBiz) ApplicationUtil.getBean("sysUserBiz");
	/*	String d="";
		if(this.getCommonId()==4233){
			System.out.println(4233);
			SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			 d=sdf.format(date);
		}*/
		SysUser tUser = sysUserBiz.getSysUserByCommonIdByDate(date,this.getCommonId());
		return tUser;
	}



}
