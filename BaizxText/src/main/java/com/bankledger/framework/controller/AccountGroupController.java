package com.bankledger.framework.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bankledger.constant.SystemConstant;
import com.bankledger.framework.bean.AccountGroupDaoBean;
import com.bankledger.framework.bean.PagingBounds;
import com.bankledger.framework.dao.DBAccountGroupDao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/accountGroup")
public class AccountGroupController {
	
	@Autowired
	DBAccountGroupDao accountGroupDao;
	
	@RequestMapping("/index")
	public String index(){
		return "system/accountGroup/index";
	}
	
	/**
	 * 查询未分组用户
	 */
	@RequestMapping("/queryUngroupAccount")
	@ResponseBody
	public Map<String,Object> queryUngroupAccount(@RequestBody Map<String,String> queryData){
		
		String offset =queryData.get("offset");
		String limit=queryData.get("limit");

		PagingBounds pagingBounds = new PagingBounds(Integer.valueOf(offset).intValue(),Integer.valueOf(limit).intValue());
		Map<String,Object> rs= new HashMap<String,Object>();
		List<AccountGroupDaoBean> data = accountGroupDao.queryUngroupAccount(queryData,pagingBounds);
		rs.put("data", data);
		rs.put("totalRecordsCount", pagingBounds.getTotal()+"");
		System.out.println(JSONArray.fromObject(rs).toString());
		return rs;
	}
	
	
	/**
	 * 查询出分组用户
	 */
	@RequestMapping("/queryGroupAccount")
	@ResponseBody
	public Map<String,Object> queryGroupAccount(@RequestBody Map<String,String> queryData){
		
		String offset =queryData.get("offset");
		String limit=queryData.get("limit");

		PagingBounds pagingBounds = new PagingBounds(Integer.valueOf(offset).intValue(),Integer.valueOf(limit).intValue());
		Map<String,Object> rs= new HashMap<String,Object>();
		List<AccountGroupDaoBean> data = accountGroupDao.queryGroupAccount(queryData,pagingBounds);
		rs.put("data", data);
		rs.put("totalRecordsCount", pagingBounds.getTotal()+"");
		System.out.println(JSONArray.fromObject(rs).toString());
		return rs;
	}
	
	/**
	 * 将选定用户移入选定组
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/addToGroup")
	@ResponseBody
	public int addToGroup(@RequestBody Map<String,Object> operationData){
		System.out.println(JSONObject.fromObject(operationData).toString());
		String groupId=(String) operationData.get("groupId");
		List<String> accountList =(List<String>) operationData.get("data");
		accountGroupDao.addToGroup(groupId, accountList);
		return SystemConstant.operationSuccess;
	}
	
	/**
	 * 将选定用户移除选定组
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/removeFromGroup")
	@ResponseBody
	public int removeFromGroup(@RequestBody Map<String,Object> operationData){
		System.out.println(JSONObject.fromObject(operationData).toString());
		String groupId=(String) operationData.get("groupId");
		List<String> accountList =(List<String>) operationData.get("data");
		accountGroupDao.removeFromGroup(groupId, accountList);
		return SystemConstant.operationSuccess;
	}
}
