package com.bankledger.security;

import java.util.List;

public interface URLPermission {
	
	public boolean isAllowedUrl(String url);
	
	public void setGroup(String groupId);
	
	public String getGroup();
	
	public void setAllowMenuUrl(List<String> menuURLList);
	
	public List<String> getAllowMenuUrl( );
}
