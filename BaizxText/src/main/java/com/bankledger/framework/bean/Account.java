package com.bankledger.framework.bean;

import com.bankledger.security.URLPermission;

public  class  Account {
	
	private String accountNo;
	private String password;
	
	
	//URL 权限相关的
	private URLPermission permission;

	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public URLPermission getPermission() {
		return permission;
	}
	public void setPermission(URLPermission permission) {
		this.permission = permission;
	}
	
	
}
