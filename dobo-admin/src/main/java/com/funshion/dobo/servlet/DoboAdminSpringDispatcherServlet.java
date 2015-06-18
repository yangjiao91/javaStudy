package com.funshion.dobo.servlet;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;

import com.funshion.dobo.utils.KafkaUtils;

/**
 * @author lirui
 *
 */
public class DoboAdminSpringDispatcherServlet extends AbstractDoboSpringDispatcherServlet {
	private static final long serialVersionUID = -1130410124566608739L;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public DoboAdminSpringDispatcherServlet() {
		super();
	}
	
	public DoboAdminSpringDispatcherServlet(WebApplicationContext webApplicationContext) {
		super(webApplicationContext);
	}
	
	@Override
	public void initFrameworkServlet() throws ServletException {
		super.initFrameworkServlet();
		KafkaUtils.initConfig();
		logger.info("Start Dobo Admin...");
	}
	
	@Override
	public void destroy() {
		logger.info("Stop Dobo Admin...");
//		shutdownQuertzSchedule();
		KafkaUtils.releaseResources();
		super.destroy();
	}
}
