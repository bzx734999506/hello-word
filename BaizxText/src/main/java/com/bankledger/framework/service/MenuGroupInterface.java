package com.bankledger.framework.service;

import java.util.List;

import com.bankledger.framework.bean.TreeMenu;

public interface MenuGroupInterface {
	
	/**
	 * 在主页上显示的菜单
	 * 1.根据groupId 加载菜单，如果是组别是0 那么将加载所有的菜单及功能项
	 * 2.否则将根据groupId 加载menu group 中的菜单，而且只加载显示型的菜单。
	 */
	public TreeMenu loadMainFrameMenu(String groupId);
	
	
	/**
	 * 权限管理里面 权限组拥有的菜单
	 * 可编辑菜单
	 * 有一些checkbox 以及一些菜单及功能项的列表......菜单及功能项 显示父菜单的菜单及功能项
	 * 但是勾选的查询到的菜单
	 */
	public TreeMenu loadEditMenu(String groupId,String parentGroupId);
	
	
	/**
	 * 根据groupId 加载TreeMenu
	 */
	public TreeMenu getTreeMenuByGroupId(String groupId);
	
	
	/**
	 * 将用户权限树设置为当前结构
	 */
	public boolean updateGroupMenu(String groupId,List<String> menuIdList);
	
	
}
