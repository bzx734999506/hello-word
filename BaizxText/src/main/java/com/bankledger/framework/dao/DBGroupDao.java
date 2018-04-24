package com.bankledger.framework.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface DBGroupDao {
	
	@Select("select * from bl_group")
	public List<Map<String,String>> queryAllGroup();
	
	@Insert("insert into BL_GROUP (GROUP_ID,GROUP_NAME,GROUP_FATHER,ICON_CLS) values(#{groupId},#{groupName},#{fatherGroupId},#{groupIcon})")
	public boolean insert(Map<String,String> data);
	
	@Update("update BL_GROUP set GROUP_NAME=#{groupName},ICON_CLS=#{groupIcon} where GROUP_ID=#{groupId}")
	public boolean update(Map<String,String> data);
	
	public boolean deleteList(List<String> deleteList);
}
