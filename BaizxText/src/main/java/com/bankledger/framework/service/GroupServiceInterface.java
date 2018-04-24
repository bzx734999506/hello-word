package com.bankledger.framework.service;

import java.util.List;
import java.util.Map;

import com.bankledger.framework.bean.TreeGroup;

public interface GroupServiceInterface {
	public TreeGroup queryGroup(String groupId);
	
	/**
	 * 新增组
	 * @param data
	 * @return
	 */
	public boolean addGroup(Map<String,String> data);
	
	/**
	 * 更新组
	 */
	public boolean updateGroup(Map<String,String> data);
	
	public boolean deleteGroupList(List<String> groupIdList);

}
