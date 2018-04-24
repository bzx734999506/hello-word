package com.bankledger.framework.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionDao {
	
	@Select("select menu.menu_url from BL_MENU_GROUP g inner join bl_menu menu on menu.menu_id=g.menu_id where g.group_id=#{groupId}")
	public List<String> getAllowMenuURLList(String groupId);
}
