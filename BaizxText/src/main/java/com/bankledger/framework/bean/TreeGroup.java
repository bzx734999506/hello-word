package com.bankledger.framework.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeGroup {
	//groupId
	private String id;
	//groupName
	private String text;
	private String fatherGroupId;
	private String fatherGroupName;
	private String iconCls;
	private List<TreeGroup> children=new ArrayList<TreeGroup>();
	
	/**
	 * 认祖归宗，如果它有父亲，将它归为父亲的孩子
	 * 参数TreeGroup treeGroup 是参数map中其中一个元素
	 * @param treeGroup
	 * @param map
	 */
	public static void addTreeGroup(TreeGroup treeGroup,Map<String,TreeGroup> map){
		String parentGroupId=treeGroup.getFatherGroupId();
		if(null!=parentGroupId){
			TreeGroup fatherGroup = map.get(parentGroupId);
			if(null!=fatherGroup){
				fatherGroup.getChildren().add(treeGroup);
				treeGroup.setFatherGroupName(fatherGroup.getText());
			}
		}
	}
	
	/**
	 * map元素里面的内容认祖归宗
	 * @param map
	 */
	public static Map<String,TreeGroup> buildTreeGroup(Map<String,TreeGroup> map){
		for(Map.Entry<String,TreeGroup> entry:map.entrySet()){
			addTreeGroup(entry.getValue(),map);
		}
		return map;
	}
	
	
	/**
	 * 将list 里面的内容都认祖归宗
	 * @return
	 */
	public static Map<String,TreeGroup>  buildTreeGroup(List<TreeGroup> list){
		Map<String,TreeGroup> map = new HashMap<String,TreeGroup>();
		for(TreeGroup treeGroup:list){
			map.put(treeGroup.getId(), treeGroup);
		}
		return buildTreeGroup(map);
	}
	
	/**
	 * 获得TreeGroup的所有 子groupId 包括自己的
	 * @param treeGroup
	 * @return
	 */
	public List<String> getAllChildrenGroupId(){
		return getAllChildrenGroupId(this);
	}
	
	/**
	 * 获得TreeGroup的所有 子groupId 包括自己的
	 * @param treeGroup
	 * @return
	 */
	public static List<String> getAllChildrenGroupId(TreeGroup treeGroup){
		List<String> rs = new ArrayList<String>();
		rs.add(treeGroup.getId());
		for(TreeGroup group:treeGroup.getChildren()){
			getAllChildrenGroupId(group,rs);
		}
		return rs;
	}
	
	/**
	 * 递归调用获得treeGroup所有子groupId
	 * @param treeGroup
	 * @param rs
	 */
	public static void getAllChildrenGroupId(TreeGroup treeGroup,List<String> rs){
		rs.add(treeGroup.getId());
		for(TreeGroup group:treeGroup.getChildren()){
			getAllChildrenGroupId(group,rs);
		}
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

	public String getFatherGroupId() {
		return fatherGroupId;
	}
	public void setFatherGroupId(String fatherGroupId) {
		this.fatherGroupId = fatherGroupId;
	}
	public String getFatherGroupName() {
		return fatherGroupName;
	}
	public void setFatherGroupName(String fatherGroupName) {
		this.fatherGroupName = fatherGroupName;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public List<TreeGroup> getChildren() {
		return children;
	}
	public void setChildren(List<TreeGroup> children) {
		this.children = children;
	}
	
}
