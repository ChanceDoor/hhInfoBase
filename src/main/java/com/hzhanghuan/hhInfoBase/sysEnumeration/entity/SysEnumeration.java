package com.hzhanghuan.hhInfoBase.sysEnumeration.entity;

/**
 * 数据字典
 */
public class SysEnumeration {
	private Long id;				//id
	private String value;			//值
	private String description;		//描述，可作为分类标签用于查询
	
	public SysEnumeration(long id) {
		this.id = id;
	}
	
	

	public SysEnumeration() {
		super();
	}



	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value = value;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setDescription(String des){
		this.description = des;
	}
}

