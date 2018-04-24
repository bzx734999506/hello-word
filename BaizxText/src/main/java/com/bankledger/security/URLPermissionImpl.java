package com.bankledger.security;

import java.util.List;

public class URLPermissionImpl implements URLPermission {
	private String groupId;
	private List<String> menuUrlList=null;
	
	public URLPermissionImpl(String groupId,List<String> menuUrlList){
		setGroup( groupId);
		setAllowMenuUrl( menuUrlList);
	}

	/**
	 *判断此url是否可以访问
	 */
	@Override
	public boolean isAllowedUrl(String url) {
		if(null!=groupId && "0".equals(groupId)){
			return true;
		}
		for(String urltmp:menuUrlList){
			if(url.equals(urltmp))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 设置用户的组
	 */
	@Override
	public void setGroup(String groupId) {
		this.groupId=groupId;
	}
		
	/**
	 * 设置可以访问的URL列表
	 */
	@Override
	public void setAllowMenuUrl(List<String> menuUrlList) {
		this.menuUrlList=menuUrlList;
	}

	@Override
	public String getGroup() {
		
		return groupId;
	}

	@Override
	public List<String> getAllowMenuUrl() {
		
		return menuUrlList;
	}

	
}
