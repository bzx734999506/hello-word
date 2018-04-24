package com.bankledger.framework.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface DBMenuDao {
	
	@Insert("insert into bl_menu(menu_id,parent_menu_id,menu_name_en,menu_name_ch,auth_flag,menu_icon,menu_type,menu_url)"
			+ "           values(#{menuId},#{parentMenuId},#{menuName},#{menuName},#{needAuthFlag},#{menuIcon},#{menuFlag},#{url})")
	boolean insert(Map<String,String> dataMap);
	
	boolean update(Map<String,String> dataMap);
	
	@Select("select * from bl_menu")
	List<Map<String,String>> getAllMenu();
	
	
	boolean deleteList(List<String> listMenuId);
	
	@Select("select menu_url from bl_menu where auth_flag='0' ")
	List<String> getNoNeedAuthMenuURL();
}
	