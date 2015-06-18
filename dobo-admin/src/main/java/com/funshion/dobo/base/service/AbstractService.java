package com.funshion.dobo.base.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * 抽象的服务类
 * @author jiangxu
 *
 */
public class AbstractService {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected DataSourceTransactionManager transactionManager;
}
