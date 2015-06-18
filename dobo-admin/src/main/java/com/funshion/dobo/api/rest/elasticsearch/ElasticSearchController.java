package com.funshion.dobo.api.rest.elasticsearch;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.funshion.dobo.api.rest.AbstractAdminController;
import com.funshion.dobo.base.service.impl.ElasticSearchService;
import com.funshion.dobo.utils.AjaxResponseUtil;


@Controller
@RequestMapping("/elasticsearch")
public class ElasticSearchController extends AbstractAdminController {
	
	
	@Autowired
	private ElasticSearchService esService;
	
	/**  从主页菜单入口进入列表 */
	@RequestMapping(value = "/list")
	public String list(HttpSession session, Map<String, Object> map) {
		return getJspPath("elasticsearch_list.jsp");
	}
	
	@RequestMapping(value = "/search")
	public void search(HttpServletRequest request,HttpServletResponse response) {
		String ret = "";
		try{
			String str = request.getParameter("keyword");
			ret = esService.searchES(request);
		}catch(Exception e){
			logger.error("ajax访问失败:" , e);
			ret = "<b>" + e.getMessage() + "</b>";
		}finally {
			AjaxResponseUtil.responseJsonString(response, ret);
		}
		
	}

	@Override
	protected String getJspPath() {
		return "/jsp/elasticsearch/";
	}
}
