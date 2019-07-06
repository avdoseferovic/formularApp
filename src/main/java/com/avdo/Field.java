package com.avdo;

public class Field {
	
	private String formName;
	
	private int fieldId;
	
	private String fieldName;
	
	private String fieldType;
	
	private String fieldValue;
	
	private String fieldMandatory;
	
	private String[] fieldRadios;
	
	
	public Field (String formName, int fieldId, String fieldName, String fieldType, String fieldValue, String fieldMandatory ,String[] fieldRadios) {
		super();
		this.formName = formName;
		this.fieldId = fieldId;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldValue = fieldValue;
		this.fieldMandatory = fieldMandatory;
		this.fieldRadios = fieldRadios;
		
	}
	
	public String getTheFormName() {
		return this.formName;
	}
	
	public String getTheName () {
		return this.fieldName;
	}
	public int getTheId () {
		return this.fieldId;
	}
	public String getTheType () {
		return this.fieldType;
	}
	public String[] getTheRadios () {
		return this.fieldRadios;
	}
	public String getTheMandatory () {
		return this.fieldMandatory;
	}

}
