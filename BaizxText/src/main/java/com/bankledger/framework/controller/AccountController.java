package com.bankledger.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bankledger.constant.SystemConstant;
import com.bankledger.framework.bean.AccountDaoBean;
import com.bankledger.framework.bean.PagingBounds;
import com.bankledger.framework.dao.DBAccountDao;

import net.sf.json.JSONArray;

@RequestMapping("/account")
@Controller
public class AccountController {
	
	private static org.apache.log4j.Logger log = Logger.getLogger(LoginController.class);
	
	@Autowired
	DBAccountDao accountDao;
	
	
	
	@RequestMapping("/index")
	public String index(){
		return "system/account/index";
	}
	
	@RequestMapping("/addShow")
	public String addShow(){	
		log.info("account addShow....");
		return "system/account/addShow";
	}
	
	@RequestMapping("/updateShow")
	public String updateShow(){	
		log.info("account updateShow....");
		return "system/account/updateShow";
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public int add(@RequestBody Map<String,String> data){
		
		accountDao.insert(data);
		return SystemConstant.operationSuccess;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public int update(@RequestBody Map<String,String> data){
		accountDao.update(data);
		return SystemConstant.operationSuccess;
	}
	
	@ResponseBody
	@RequestMapping("/delete")
	public int delete(@RequestBody Map<String,String> data){
		accountDao.delete(data);
		return SystemConstant.operationSuccess;
	}
	
	@ResponseBody
	@RequestMapping("/query")
	public Map<String,Object> query(@RequestBody Map<String,String> queryData){
		String offset =queryData.get("offset");
		String limit=queryData.get("limit");
		System.out.println("offset:"+offset+"  limit:"+limit);

		PagingBounds pagingBounds = new PagingBounds(Integer.valueOf(offset).intValue(),Integer.valueOf(limit).intValue());
		Map<String,Object> rs= new HashMap<String,Object>();
		List<AccountDaoBean> data = accountDao.query(queryData,pagingBounds);
		rs.put("data", data);
		rs.put("totalRecordsCount", pagingBounds.getTotal()+"");
		System.out.println(JSONArray.fromObject(rs).toString());
		return rs;
	}
}
