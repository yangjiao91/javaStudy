package com.funshion.dobo.api.rest.topic;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.funshion.dobo.base.dao.entity.Topic;
import com.funshion.dobo.base.dao.entity.TopicSchema;
import com.funshion.dobo.base.service.impl.TopicService;
import com.funshion.dobo.utils.AjaxResponseUtil;
import com.funshion.dobo.utils.AjaxResponseUtil.AjaxResponse;


/**
 * 
 * @author lirui
 *
 */
@Controller
@RequestMapping("/topic/simple")
public class SimpleTopicController extends TopicController {
	
	/** 页面中topic_simple_list标签页的id */
	public static final String TOPIC_LIST_TAB_ID = "topic_simple_list";
	
	@Autowired
	private TopicService topicService;
	
	/** 点击列表的查询或上下分页等 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(TopicPagerForm pagerForm, Map<String, Object> map) {
		map.put("pagerForm", pagerForm);
		map.put("list", topicService.findSimpleTopicListAutoPaging(pagerForm));
		return getJspPath("topic_list.jsp");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void doAdd(Topic topic, TopicSchema topicSchema, HttpServletResponse response) {
		AjaxResponse ret = new AjaxResponse();
		try {
			topicService.addTopic(topic);
			closeDialogAndRefreshTab(ret, TOPIC_LIST_TAB_ID, "添加成功");
		} catch (Exception e) {
			genAjaxErrorInfo(ret, e);
		} finally {
			AjaxResponseUtil.responseJsonObject(response, ret);
		}
	}
	
	@RequestMapping(value = "/doedit", method = RequestMethod.POST)
	public void doEdit(Topic topic, HttpServletResponse response) {
		AjaxResponse ret = new AjaxResponse();
		try {
			topicService.update(topic);
			closeDialogAndRefreshTab(ret, TOPIC_LIST_TAB_ID, "修改成功");
		} catch (Exception e) {
			genAjaxErrorInfo(ret, e);
		} finally {
			AjaxResponseUtil.responseJsonObject(response, ret);
		}
	}
	
	@Override
	protected String getJspPath() {
		return "/jsp/topic/simple/";
	}
}
