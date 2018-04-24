package com.bankledger.utils;

import java.sql.Connection;

public class PageSqlUtils {
	
	public static String getTotalSql(Connection conn,String sql) throws Exception{
		String dbType = conn.getMetaData().getDatabaseProductName();
		if("oracle".equals(dbType.toLowerCase()))
		{
			return getOracleTotalSql( sql);
		}
		else if("mysql".equals(dbType.toLowerCase())){
			return getMysqlTotalSql(sql);
		}
		return null;
	}
	
	public static String getPageSql(Connection conn,String sql,int offset, int limit) throws Exception{
		String dbType = conn.getMetaData().getDatabaseProductName();
		if("oracle".equals(dbType.toLowerCase()))
		{
			return getOraclePageSql( sql,offset,limit);
		}
		else if("mysql".equals(dbType.toLowerCase())){
			return getMysqlPageSql(sql,offset,limit);
		}
		return null;
		
	}
	
	public static String getOracleTotalSql(String sql){	
		StringBuilder rs = new StringBuilder();
		rs.append("select count(1) total_ from (").append(sql).append(") total_ ");
		return rs.toString();
	}
	
	public static String getMysqlTotalSql(String sql){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT COUNT(1) FROM (").append(sql).append(") T");
		return sb.toString();		
	}
	
	public static String getOraclePageSql(String sql,int offset, int limit){
		StringBuilder pageSql = new StringBuilder();    
		pageSql.append("select * from ( select row_.*, rownum rownum_ from ( ");    
		pageSql.append(sql); 
		pageSql.append(" ) row_ ) where rownum_ > ").append(offset)
			 .append(" and rownum_ <= ").append(offset + limit);    
		return pageSql.toString();	
	}
	
	public static String getMysqlPageSql(String sql,int offset, int limit){
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM (").append(sql).append(") T LIMIT ")
				.append(offset).append(",").append(limit);
		return sb.toString();
	}
}
