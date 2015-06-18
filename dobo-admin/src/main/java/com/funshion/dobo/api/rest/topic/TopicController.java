package com.funshion.dobo.api.rest.topic;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.funshion.dobo.api.rest.AbstractAdminController;
import com.funshion.dobo.base.dao.entity.Topic;
import com.funshion.dobo.base.dao.entity.TopicSchema;
import com.funshion.dobo.base.service.impl.SchemaService;
import com.funshion.dobo.base.service.impl.TopicService;
import com.funshion.dobo.utils.AjaxResponseUtil;
import com.funshion.dobo.utils.AjaxResponseUtil.AjaxResponse;


/**
 * 
 * @author lirui
 *
 */
@Controller
@RequestMapping("/topic")
public class TopicController extends AbstractAdminController {
	
	/** 页面中topic_list标签页的id */
	public static final String TOPIC_LIST_TAB_ID = "topic_list";
	/** 页面中topic_list标签页的id */
	public static final String TOPIC_SIMPLE_LIST_TAB_ID = "topic_simple_list";
	
	@Autowired
	private TopicService topicService;
	@Autowired
	private SchemaService schemaService;
	
	/**  从主页菜单入口进入列表 */
	@RequestMapping(value = "/list")
	public String list(HttpSession session, Map<String, Object> map) {
		return search(new TopicPagerForm(), map);
	}
	
	/** 点击列表的查询或上下分页等 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(TopicPagerForm pagerForm, Map<String, Object> map) {
		map.put("pagerForm", pagerForm);
		List<Topic> topicList = topicService.findTopicListAutoPaging(pagerForm);
		for (Topic topic : topicList) {
			topicService.setSchemaStatus(topic);
		}
		map.put("list", topicList);
		return getJspPath("topic_list.jsp");
	}
	
	@RequestMapping(value = "/add")
	public String showAdd(HttpSession session, Map<String, Object> map) {
		map.put("fixedFields", topicService.getFixedFields());
		return getJspPath("topic_add.jsp");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void doAdd(Topic topic, TopicSchema topicSchema, HttpServletResponse response) {
		AjaxResponse ret = new AjaxResponse();
		try {
			topicService.addTopicAndSchema(topic, topicSchema);
			closeDialogAndRefreshTab(ret, TOPIC_LIST_TAB_ID, "添加成功");
		} catch (Exception e) {
			genAjaxErrorInfo(ret, e);
		} finally {
			AjaxResponseUtil.responseJsonObject(response, ret);
		}
	}
	
	@RequestMapping(value = "/del/{topicId}")
	public void doDel(@PathVariable("topicId") long topicId,HttpServletResponse response) {
		AjaxResponse ret = new AjaxResponse();
		try {
			topicService.delTopic(topicId);
			closeDialogAndRefreshTab(ret, TOPIC_LIST_TAB_ID, "删除成功");
		} catch (Exception e) {
			genAjaxErrorInfo(ret, e);
		} finally {
			AjaxResponseUtil.responseJsonObject(response, ret);
		}
	}
	
	@RequestMapping(value = "/audit/{topicId}", method = RequestMethod.POST)
	public void doAudit(@PathVariable("topicId") long topicId, HttpServletResponse response) {
		AjaxResponse ret = new AjaxResponse();
		try {
			topicService.auditTopic(topicId);
			closeDialogAndRefreshTab(ret, TOPIC_LIST_TAB_ID, "审核成功, 并已经在Kafka服务端创建topic成功");
		} catch (Exception e) {
			genAjaxErrorInfo(ret, e);
		} finally {
			AjaxResponseUtil.responseJsonObject(response, ret);
		}
	}
	
	@RequestMapping(value = "/audit/{topicId}", method = RequestMethod.GET)
	public String showAudit(@PathVariable("topicId") long topicId, HttpSession session, Map<String, Object> map) {
		map.put("topic", topicService.getTopic(topicId));
		map.put("schema", schemaService.getPendingSchema(topicId));
		return getJspPath("topic_audit.jsp");
	}
	
	@RequestMapping(value = "/edit/{topicId}", method = RequestMethod.GET)
	public String showEdit(@PathVariable("topicId") long topicId, HttpSession session, Map<String, Object> map) {
		map.put("topic", topicService.getTopic(topicId));
		map.put("schema", schemaService.getPendingSchema(topicId));
		map.put("fixedFields", topicService.getFixedFields());
		return getJspPath("topic_edit.jsp");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public void doEdit(long topicId, long schemaId, Topic topic, TopicSchema schema, HttpServletResponse response) {
		AjaxResponse ret = new AjaxResponse();
		try {
			topic.setId(topicId);
			schema.setId(schemaId);
			topicService.update(topic, schema);
			closeDialogAndRefreshTab(ret, TOPIC_LIST_TAB_ID, "修改成功");
		} catch (Exception e) {
			genAjaxErrorInfo(ret, e);
		} finally {
			AjaxResponseUtil.responseJsonObject(response, ret);
		}
	}
	
	@Override
	protected String getJspPath() {
		return "/jsp/topic/";
	}
}
