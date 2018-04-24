package com.bankledger.framework.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.bankledger.framework.bean.MenuGroupDaoBean;
@Repository
public interface DBMenuGroupDao {

	boolean deleteList(List<String> menuIdList);
	
	boolean deleteListByGroupIdList(List<String> data);
	
	@Select("select MENU_ID as menuId ,GROUP_ID as groupId from BL_MENU_GROUP where GROUP_ID=#{param1,jdbcType=VARCHAR}")
	List<MenuGroupDaoBean> queryByGroupId(String groudId);
	
	boolean insertList(String groupId,List<String> menuIdList);
	
	@Delete("delete from BL_MENU_GROUP where GROUP_ID=#{param1}")
	boolean deleteByGroupId(String groupId);
	
}
