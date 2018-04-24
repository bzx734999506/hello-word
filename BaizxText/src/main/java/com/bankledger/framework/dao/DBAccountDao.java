package com.bankledger.framework.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.bankledger.framework.bean.Account;
import com.bankledger.framework.bean.AccountDaoBean;
import com.bankledger.framework.bean.PagingBounds;

@Repository
public interface DBAccountDao {
	
	@Select("select count(1) from bl_account where account_no=#{accountNo} and password=#{password}")
	public int isValidUser(Account account);
	
	@Insert("insert into bl_account (ACCOUNT_NO,PASSWORD,ACCOUNT_ICON) values(#{accountNo},#{password},#{iconCls})")
	public boolean insert(Map<String,String> account);
	
	
	public List<AccountDaoBean> query(Map<String,String> queryData,PagingBounds pagingBounds);
	
	@Update("update bl_account set ACCOUNT_ICON=#{iconCls} where account_no=#{accountNo}")
	public boolean update(Map<String,String> daoData);
	
	@Delete("delete from bl_account where account_no=#{accountNo}")
	public boolean delete(Map<String,String> daoData);
	
}
