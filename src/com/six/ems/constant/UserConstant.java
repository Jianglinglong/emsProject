package com.six.ems.constant;

public enum UserConstant {
	STUDENT("stu"),
	TEACHER("tea"),
	ROOT_MENU("100001");
	
	private String value = null;
	
	private UserConstant(String value) {
		this.value=value;
		
	}
	
	public String getValue() {
		return value;
	}

	
	
	
}
