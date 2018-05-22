package com.xxdai.person.model;

public class UpdateFieldEntity {
	/**字段名称*/
	private String fieldName;
	
	/**字段值*/
	private String fieldValue;
	
	/**信用权重*/
	private int addScore;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public int getAddScore() {
		return addScore;
	}

	public void setAddScore(int addScore) {
		this.addScore = addScore;
	}
	
	
}
