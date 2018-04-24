package com.bankledger.framework.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.bankledger.constant.SystemConstant;
import com.bankledger.framework.bean.Account;
import com.bankledger.framework.bean.TreeMenu;
import com.bankledger.framework.service.MenuGroupInterface;

@Controller
@RequestMapping("/menuGroup")
public class MenuGroupController {
	
	@Resource(name="menuGroupService")
	MenuGroupInterface menuGroupService;
	
	private static Logger log =Logger.getLogger(MenuGroupController.class);
	
	@RequestMapping("/index")
	public ModelAndView index(ModelAndView mv,HttpServletRequest request){
		log.info("menuGroup....index");
		mv.setViewName("system/menuGroup/index");
		return mv;
	}
	
	@ResponseBody
	@RequestMapping("/loadMenuByGroupId")
	public List<TreeMenu> loadMenuByGroupId(HttpServletRequest request,@RequestBody Map<String,String> data){
		String groupId= data.get("groupId");
		String parentGroupId=data.get("fatherGroupId");
		List<TreeMenu> rs = new ArrayList<TreeMenu>();
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute("ACCOUNT");
		String currUserGroupId=account.getPermission().getGroup();
		if(currUserGroupId.equals(groupId)){
			parentGroupId=groupId;
		}
		rs.add(menuGroupService.loadEditMenu(groupId, parentGroupId));
		return rs;
	}
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping("/updateMenuTree")
	public int updateMenuTree(@RequestBody Map<String,Object> postData){
		String groupId=(String) postData.get("groupId");
		List<String> menuIdList = (List<String>) postData.get("data");
		menuGroupService.updateGroupMenu(groupId, menuIdList);
		return SystemConstant.operationSuccess;
	}
}
