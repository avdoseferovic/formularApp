package com.avdo;

public class Form {
	
	private String formVersion;
	
	private String formName;
	
	private int fieldId;
	
	private String fieldValue;
	
	
	public Form(String formVersion, String formName, int fieldId, String fieldValue) {
		super();
		this.formVersion = formVersion;
		this.formName = formName;
		this.fieldId = fieldId;
		this.fieldValue = fieldValue;
	
	}
	
	public String getTheVersion() {
		return formVersion;
	}
	
	public String getTheName() {
        return formName;
    }
	public int getTheFieldId() {
		return fieldId;
	}
	public String getTheFieldValue() {
		return fieldValue;
	}
}
