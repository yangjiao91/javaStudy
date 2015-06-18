package com.funshion.dobo.base.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.parameter.DefaultParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.funshion.dobo.api.rest.AbstractPagerForm;
import com.funshion.lego.utils.ReflectHelper;


/**
 * 用于分页的 mybatis 拦截器
 * @author jiangxu
 * 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {
	protected final Logger logger = LoggerFactory.getLogger("com.funshion.nami.mybatis.persistence");
	
	/** sqlMapConfig.xml 中设置要拦截的方法的正则匹配 */
	private static String pageSqlIdRegExp = "";

	@SuppressWarnings({"rawtypes" })
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		if (invocation.getTarget() instanceof RoutingStatementHandler) {
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation.getTarget();
			BaseStatementHandler delegate = (BaseStatementHandler)ReflectHelper.getValueByFieldName(statementHandler, "delegate");
			MappedStatement mappedStatement = (MappedStatement) ReflectHelper.getValueByFieldName(delegate, "mappedStatement" );
			
			if (mappedStatement.getId().matches(pageSqlIdRegExp)) {
				// 对指定正则表达式的 sql id 进行拦截
				BoundSql boundSql = delegate.getBoundSql();
				Object parameterObject = boundSql.getParameterObject();
				
				AbstractPagerForm pagerForm = (AbstractPagerForm)((MapperMethod.MapperParamMap)parameterObject).get("param1");
				Connection connection = (Connection)invocation.getArgs()[0];
				String baseSql = boundSql.getSql();
				countRecords(connection, pagerForm, mappedStatement, parameterObject, boundSql, baseSql);
				
				StringBuilder pageSql = new StringBuilder(pagerForm.getSqlHeaderForSelect()).append(baseSql);
				if (!pagerForm.isDisableAutoPage()) {
					pageSql.append(" limit ").append(pagerForm.getPageStart()).append(" , ")
							.append(pagerForm.getPageSize());
				}
						
				ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql.toString());
			}
		}

		return invocation.proceed();
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {
		Object value = (Object)properties.get("pageSqlIdRegExp");
		if (!(value instanceof String) || StringUtils.isBlank((String) value)) {
			logger.error("\"pageSqlIdRegExp\" property is not found!");
			throw new RuntimeException("\"pageSqlIdRegExp\" property is not found!");
		}
		
		pageSqlIdRegExp = ((String)value).trim();
		logger.info("\"pageSqlIdRegExp\" property is \"{}\"", pageSqlIdRegExp);
	}
	
	/** 
	 * 查询一共多少满足条件的记录，并修正分页参数
	 */
	private void countRecords(
			Connection connection,
			AbstractPagerForm pagerForm, 
			MappedStatement mappedStatement,
			Object parameterObject,
			BoundSql boundSql,
			String baseSql) {
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			String countSql = pagerForm.getSqlHeaderForCount().concat(baseSql);
			countStmt = connection.prepareStatement(countSql);
			DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
			parameterHandler.setParameters(countStmt);
			rs = countStmt.executeQuery();
			int count = 0;
			if(rs.next()){
				count = rs.getInt(1);
			}
			
			pagerForm.setTotalCount(count);
			int totalPageCount = count / pagerForm.getPageSize();
			if (count % pagerForm.getPageSize() != 0) {
				totalPageCount++;
			}
			pagerForm.setTotalPageCount(totalPageCount);
			
			if (pagerForm.getPageCurrent() > totalPageCount) {
				pagerForm.setPageCurrent(totalPageCount);
			}
			
			if (pagerForm.getPageCurrent() < 1) {
				pagerForm.setPageCurrent(1);
			}
			
			pagerForm.setPageStart((pagerForm.getPageCurrent() - 1) * pagerForm.getPageSize());
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.warn("Fail to close rs.");
				}
			}
			
			if (countStmt != null) {
				try {
					countStmt.close();
				} catch (SQLException e) {
					logger.warn("Fail to close countStmt.");
				}
			}
		}
	}
}
