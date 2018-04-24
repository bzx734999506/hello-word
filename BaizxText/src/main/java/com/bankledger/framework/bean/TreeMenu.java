package com.bankledger.framework.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeMenu {
	
	private String id;
	private String text;
	private String needAuthFlag;
	private String iconCls;
	private String menuFlag;
	private String parentMenuId;
	private String url;
	private boolean checked=false;
	private String state="open";
	
	private List<TreeMenu> children=new ArrayList<TreeMenu>();
	
	/**
	 * 将menu 菜单认祖归宗
	 */
	public  static void  addMenu(TreeMenu menu,Map<String, TreeMenu> treeMenuMap){
		String myParentMenuId=menu.parentMenuId;
		if(null!=myParentMenuId){
			TreeMenu treeMenu = treeMenuMap.get(myParentMenuId);
			if(null!=treeMenu)
			{
				treeMenu.getChildren().add(menu);
			}
		}		
	}
	
	/**
	 * map元素里面的内容认祖归宗
	 * @param map
	 */
	public static Map<String,TreeMenu> buildTreeGroup(Map<String,TreeMenu> map){
		for(Map.Entry<String,TreeMenu> entry:map.entrySet()){
			addMenu(entry.getValue(),map);
		}
		return map;
	}
	
	/**
	 * 递归调用，获取所有的子菜单
	 * @param treeMenu
	 * @param idList
	 */
	public static void getAllChildrenMenuId(TreeMenu treeMenu,List<String> idList){
		idList.add(treeMenu.getId());
		List<TreeMenu> children = treeMenu.getChildren();
		for(TreeMenu menu:children){
			getAllChildrenMenuId(menu,idList);
		}
	}
	
	/**
	 * 获取该菜单的所有子菜单ID
	 * @return
	 */
	public List<String> getAllChildrenMenuId(){
		List<String> idList = new ArrayList<String>();
		getAllChildrenMenuId(this,idList);	
		return idList;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getNeedAuthFlag() {
		return needAuthFlag;
	}
	public void setNeedAuthFlag(String needAuthFlag) {
		this.needAuthFlag = needAuthFlag;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getMenuFlag() {
		return menuFlag;
	}
	public void setMenuFlag(String menuFlag) {
		this.menuFlag = menuFlag;
	}
	public String getParentMenuId() {
		return parentMenuId;
	}
	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}
	public List<TreeMenu> getChildren() {
		return children;
	}
	public void setChildren(List<TreeMenu> children) {
		this.children = children;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
