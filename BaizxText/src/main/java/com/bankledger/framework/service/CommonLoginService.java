package com.bankledger.framework.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankledger.encrypt.RSA;
import com.bankledger.framework.bean.Account;
import com.bankledger.framework.dao.DBAccountDao;
import com.bankledger.framework.dao.DBAccountGroupDao;
import com.bankledger.framework.dao.DBMenuDao;
import com.bankledger.framework.dao.PermissionDao;
import com.bankledger.security.URLPermission;
import com.bankledger.security.URLPermissionImpl;
import com.bankledger.springBean.MyProperties;

@Service("CommonLoginService")
public class CommonLoginService implements LoginServiceInterface{

	@Autowired
	DBAccountDao accountDao;
	
	@Autowired
	DBAccountGroupDao  accountGroupDao;
	
	@Autowired
	DBMenuDao   menuDao;
	
	@Autowired
	PermissionDao  permissionDao;
	
	@Override
	public boolean login(Account account) throws Exception {
		processEncrypt(account);
		boolean rs= (1==accountDao.isValidUser(account));
		processPermission(account);
		return rs;
	}
	
	public void processPermission(Account account){
		String accountNo=account.getAccountNo();
		String groupId=accountGroupDao.getGroupId(accountNo);
		URLPermission permission=null;
		List<String> allowMenuList = null;
		if(null==groupId){
			allowMenuList = menuDao.getNoNeedAuthMenuURL();			
		}
		else
		{
			allowMenuList=permissionDao.getAllowMenuURLList(groupId);
			allowMenuList.addAll(menuDao.getNoNeedAuthMenuURL());
		}
		permission = new URLPermissionImpl(groupId,allowMenuList);
		account.setPermission(permission);
	}
	
	public void processEncrypt(Account account) throws Exception{
		
		if("true".equals(MyProperties.getMyProperties().getProperty("login.transfer.encrypt.flag", "false"))){
			account.setPassword(RSA.decryptBase64AndReturnWithPlainText(account.getPassword()));
		}
		
	}

}
