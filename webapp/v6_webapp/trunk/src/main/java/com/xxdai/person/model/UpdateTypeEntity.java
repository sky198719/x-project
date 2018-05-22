package com.xxdai.person.model;

public class UpdateTypeEntity {
	/**资料类型名称*/
	private String typeName;
	
	/**字段实体*/
	private UpdateFieldEntity[] updateFieldEntity;

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public UpdateFieldEntity[] getUpdateFieldEntity() {
		return updateFieldEntity;
	}

	public void setUpdateFieldEntity(UpdateFieldEntity[] updateFieldEntity) {
		this.updateFieldEntity = updateFieldEntity;
	}
	
	
}
