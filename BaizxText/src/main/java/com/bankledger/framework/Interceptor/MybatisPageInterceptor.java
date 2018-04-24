package com.bankledger.framework.Interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import com.bankledger.framework.bean.PagingBounds;
import com.bankledger.utils.PageSqlUtils;
import com.bankledger.utils.RefectionUtils;


@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class }) })
public class MybatisPageInterceptor implements Interceptor {
	private static final Pattern PATTERN_SQL_BLANK = Pattern.compile("\\s+");

	private static final String FIELD_DELEGATE = "delegate";

	private static final String FIELD_ROWBOUNDS = "rowBounds";

	private static final String FIELD_MAPPEDSTATEMENT = "mappedStatement";

	private static final String FIELD_SQL = "sql";

	private static final String BLANK = " ";


	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		RoutingStatementHandler statementHandler = (RoutingStatementHandler) invocation.getTarget();
		Connection conn = (Connection)invocation.getArgs()[0];	
		StatementHandler handler = (StatementHandler) RefectionUtils.readField(statementHandler, FIELD_DELEGATE);
		MappedStatement mappedStatement = (MappedStatement) RefectionUtils.readField(handler, FIELD_MAPPEDSTATEMENT);
		RowBounds rowBounds = (RowBounds) RefectionUtils.readField(handler, FIELD_ROWBOUNDS);	
		if(rowBounds instanceof PagingBounds){
			PagingBounds pagingBounds = (PagingBounds)rowBounds;
			BoundSql boundSql = handler.getBoundSql();
			Object parameterObject = boundSql.getParameterObject();
			String targetSql = replaceSqlBlank(boundSql.getSql());
			
			//获取总记录数
			String totalSql =PageSqlUtils.getTotalSql(conn, targetSql);		
			List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
			BoundSql totalBoundSql = new BoundSql(mappedStatement.getConfiguration(), totalSql, parameterMappings, parameterObject);
			ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, totalBoundSql);
			pagingBounds.setTotal(getTotalRecordCount(conn,totalSql,parameterHandler));//设置总记录数
			
			//替换分页sql
			String pageSql =PageSqlUtils.getPageSql(conn, targetSql,pagingBounds.getOffset(),pagingBounds.getLimit());
			RefectionUtils.writeDeclaredField(boundSql, FIELD_SQL, pageSql);
			
			pagingBounds.setMeToDefault(); 
		}
		
		return invocation.proceed();
	}
	
	private String replaceSqlBlank(String originalSql) {
	    Matcher matcher = PATTERN_SQL_BLANK.matcher(originalSql);
	    return matcher.replaceAll(BLANK);
	}
	
	public int getTotalRecordCount(Connection conn,String totalSql,ParameterHandler parameterHandler) throws Exception{
		 PreparedStatement pstmt = null;
		 ResultSet rs = null;
		 try {
		     pstmt = conn.prepareStatement(totalSql);
		     parameterHandler.setParameters(pstmt);
		     rs = pstmt.executeQuery();
		     if(rs.next()) {
		         return  rs.getInt(1);
		 
		     }
		 } finally {
		     if(rs != null) {
		         rs.close();
		     }
		     if(pstmt != null) {
		         pstmt.close();
		     }
		 }		
		return -1;
	}


	@Override
	public Object plugin(Object target) {
		System.out.println(target);
		return Plugin.wrap(target, this);
		
	}

	@Override
	public void setProperties(Properties properties) {
		properties.list(System.out);
	}
	
	
}
