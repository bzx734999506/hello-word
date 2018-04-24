package com.bankledger.framework.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bankledger.constant.SystemConstant;
import com.bankledger.framework.bean.Account;
import com.bankledger.framework.bean.TreeMenu;
import com.bankledger.framework.dao.DBMenuDao;
import com.bankledger.framework.dao.DBMenuGroupDao;
import com.bankledger.framework.service.MenuGroupInterface;
import com.bankledger.framework.service.MenuServiceInterface;

import net.sf.json.JSONArray;

@Controller
public class MenuController {
	
	@Autowired
	DBMenuDao menuDao;
	
	@Autowired
	DBMenuGroupDao menuGroupDao;
	
	@Resource(name="menuGroupService")
	MenuGroupInterface menuGroupService;
	
	@Resource(name="menuService")
	MenuServiceInterface menuService;
	
	/**
	 * 加载菜单数据
	 * @param request
	 * @return
	 */
	@RequestMapping("/loadMenu")
	@ResponseBody
	public List<TreeMenu> loadMenu(HttpServletRequest request){
		
		HttpSession session = request.getSession(true);
		Account account =(Account) session.getAttribute("ACCOUNT");
		String groupId =account.getPermission().getGroup();
		List<TreeMenu> listTreeMenu = new ArrayList<TreeMenu>();
		listTreeMenu.add(menuGroupService.loadMainFrameMenu(groupId));
		System.out.println(JSONArray.fromObject(listTreeMenu).toString());
		return listTreeMenu;
	}

	/**
	 * 新增菜单界面
	 * @return
	 */
	@RequestMapping("/addMenuShow")
	public String addMenuShow(){	
		return "system/home/addMenu";
	}
	
	/**
	 * 新增菜单界面
	 * @return
	 */
	@RequestMapping("/updateMenuShow")
	public String updateMenuShow(){	
		return "system/home/updateMenu";
	}
	
	/**
	 * 新增菜单
	 * @param addMenuDataMap
	 * @return
	 */
	@RequestMapping("/addMenu")
	@ResponseBody
	public long addMenu(@RequestBody Map<String,String> addMenuDataMap){	
		long menuId= System.currentTimeMillis();
		addMenuDataMap.put("menuId", String.valueOf(menuId));
		menuDao.insert(addMenuDataMap);
		return menuId;
	}
	

	/**
	 * 更新菜单
	 */
	@RequestMapping("/updateMenu")
	@ResponseBody
	public int updateMenu(@RequestBody Map<String,String> updateMenuDataMap){	
		menuDao.update(updateMenuDataMap);
		return SystemConstant.operationSuccess;
	}
	
	
	/**
	 * 删除菜单
	 * @param menuId
	 * @return
	 */
	@RequestMapping("/deleteMenu")
	@ResponseBody
	public int deleteMenu(@RequestBody Map<String,String> deleteMap){
		String menuId=deleteMap.get("MENUID");
		//获取要被删除的menu
		menuService.deleteMenu(menuId);

		return SystemConstant.operationSuccess;
	}
	
	
	
	
}
