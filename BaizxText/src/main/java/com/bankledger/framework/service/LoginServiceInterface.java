package com.bankledger.framework.service;

import com.bankledger.framework.bean.Account;

public interface LoginServiceInterface {

	/**
	 * 
	 * @param account
	 * @return
	 */
	public boolean login(Account account)throws Exception;
	
}
