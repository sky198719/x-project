package com.xxdai.person.model;

public class AddTypeEntity {
	
	/**类型名称*/
	private String typeName;
	
	/**字段数组*/
	private AddFieldEntity[] addFieldEntity;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public AddFieldEntity[] getAddFieldEntity() {
		return addFieldEntity;
	}

	public void setAddFieldEntity(AddFieldEntity[] addFieldEntity) {
		this.addFieldEntity = addFieldEntity;
	}
}
