package com.bankledger.test.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankledger.test.bean.Text;
import com.bankledger.test.dao.DBTextDao;
import com.fasterxml.jackson.databind.util.BeanUtil;

@Service("TextService")
public class TextService implements TextServiceInterface {
	
	@Autowired
	DBTextDao DBtextDao;
	
	@Override
	public List<Text> queryIndex() {
		Text aText =new Text();
		aText.setUserid("1");
		aText.setPassword("123");
		
		Text bText =new Text();
		bText.setUserid("2");
		bText.setPassword("456");
		try {
			BeanUtils.copyProperties(aText, bText);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		System.out.println(aText.getPassword());
		System.out.println(aText.getUserid());
		System.out.println(bText.getPassword());
		System.out.println(bText.getUserid());
		
		return DBtextDao.queryIndex();	
	}

}
