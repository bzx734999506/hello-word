package com.bankledger.framework.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.bankledger.framework.bean.AccountGroupDaoBean;
import com.bankledger.framework.bean.PagingBounds;

@Repository
public interface DBAccountGroupDao {
	
	@Select("select group_id from BL_ACCOUNT_GROUP where account_no=#{accountNo}")
	public String getGroupId(String accountNo);
	
	public List<AccountGroupDaoBean> queryUngroupAccount(Map<String,String> queryData,PagingBounds pagingBounds);
	
	public List<AccountGroupDaoBean> queryGroupAccount(Map<String,String> queryData,PagingBounds pagingBounds);
	
	public boolean addToGroup(String groupId,List<String> account);
	
	public boolean removeFromGroup(String groupId,List<String> account);
	
	public boolean deleteListGroup(List<String> groupIdList);
}
