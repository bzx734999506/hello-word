package com.bankledger.framework.controller;



import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bankledger.constant.SystemConstant;
import com.bankledger.framework.bean.Account;
import com.bankledger.framework.bean.TreeGroup;
import com.bankledger.framework.service.GroupServiceInterface;

@Controller
@RequestMapping("/group")
public class GroupController {

	private static org.apache.log4j.Logger log = Logger.getLogger(GroupController.class);
	
	@Resource(name="GroupService")
	GroupServiceInterface groupService;

	@RequestMapping("/queryGroup")
	@ResponseBody
	public TreeGroup[] queryGroup(HttpServletRequest request,@RequestBody Map<String,String> queryData){
		
		String groupId=queryData.get("GROUPID");
		if(null==groupId){
			HttpSession session = request.getSession();
			Account account = (Account) session.getAttribute("ACCOUNT");
			groupId=account.getPermission().getGroup();
		}
		log.info("/group/queryGroup:"+groupId);
		return new TreeGroup[]{groupService.queryGroup(groupId)};
	}
	
	@RequestMapping("/addShow")
	public String addShow(){
		return "system/menuGroup/addShow";
	}
	
	@RequestMapping("/updateShow")
	public String updateShow(){
		return "system/menuGroup/updateShow";
	}
	
	/**
	 * 新增组
	 */
	@RequestMapping("/add")
	@ResponseBody
	public long add(@RequestBody Map<String,String> data){
		long groupId = System.currentTimeMillis();
		data.put("groupId", String.valueOf(groupId));
		groupService.addGroup(data);
		return groupId;
	}
	
	@ResponseBody
	@RequestMapping("/update")
	public int update(@RequestBody Map<String,String> data){
		 groupService.updateGroup(data);
		 return SystemConstant.operationSuccess;
	}
	
	/**
	 * Group Id 为0，不允许删除，系统的根组
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public int deleteByGroupId(@RequestBody Map<String,String> data){
		String groupId=data.get("groupId");
		if(!"0".equals(groupId)){
			TreeGroup  treeGroup=groupService.queryGroup(groupId);
			List<String> deleteGroupIdList = treeGroup.getAllChildrenGroupId();
			groupService.deleteGroupList(deleteGroupIdList);
		}
		return SystemConstant.operationSuccess;
	}
}
