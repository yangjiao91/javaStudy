package com.funshion.dobo.api.rest.schema;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.funshion.dobo.api.rest.AbstractAdminController;
import com.funshion.dobo.api.rest.topic.TopicController;
import com.funshion.dobo.base.dao.entity.TopicSchema;
import com.funshion.dobo.base.exception.DoboAdminException;
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
@RequestMapping("/schema")
public class SchemaController extends AbstractAdminController {
	
	/** 页面中schema_list标签页的id */
	private static final String SCHEMA_LIST_TAB_ID = "schema_list";
	
	@Autowired
	private SchemaService schemaService;
	@Autowired
	private TopicService topicService;
	
	/**  从主页菜单入口进入列表 */
	@RequestMapping(value = "/list")
	public String list(SchemaPagerForm pagerForm, HttpSession session, Map<String, Object> map) {
		return search(pagerForm, map);
	}
	
	/** 点击列表的查询或上下分页等 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(SchemaPagerForm pagerForm, Map<String, Object> map) {
		map.put("pagerForm", pagerForm);
		map.put("list", schemaService.findSchemaListAutoPaging(pagerForm));
		return getJspPath("schema_list.jsp");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String showAdd(HttpSession session, Map<String, Object> map) {
		// 添加schema需要选择尚未创建schema的topic
		map.put("fixedFields", topicService.getFixedFields());
		map.put("topicList", topicService.getNoSchemaTopics());
		return getJspPath("schema_add.jsp");
	}
	
	@RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
	public String showView(@PathVariable("id") long id, HttpSession session, Map<String, Object> map) {
		map.put("schema", schemaService.getSchema(id));
		return getJspPath("schema_view.jsp");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void doAdd(TopicSchema schema, HttpServletResponse response) {
		AjaxResponse ret = new AjaxResponse();
		try {
			schemaService.addSchema(schema, false);
			closeDialogAndRefreshTab(ret, SCHEMA_LIST_TAB_ID, "添加成功");
		} catch (Exception e) {
			genAjaxErrorInfo(ret, e);
		} finally {
			AjaxResponseUtil.responseJsonObject(response, ret);
		}
	}
	
	@RequestMapping(value = "/del/{id}", method = RequestMethod.POST)
	public void doDel(@PathVariable("id") long id, HttpServletResponse response) {
		AjaxResponse ret = new AjaxResponse();
		try {
			schemaService.delSchema(id);
			refreshTab(ret, SCHEMA_LIST_TAB_ID, "删除成功");
		} catch (Exception e) {
			genAjaxErrorInfo(ret, e);
		} finally {
			AjaxResponseUtil.responseJsonObject(response, ret);
		}
	}
	
	@RequestMapping(value = "/update/{topicId}", method = RequestMethod.GET)
	public String showUpdate(@PathVariable("topicId") long topicId, Map<String, Object> map, HttpServletResponse response) {
		TopicSchema schema = schemaService.getLatestCheckedSchema(topicId);
		if (schema == null) {
			AjaxResponse ret = new AjaxResponse();
			genAjaxErrorInfo(ret, new DoboAdminException("该topic还没有schema，请先注册schema"));
			AjaxResponseUtil.responseJsonObject(response, ret);
			return null;
		}
		map.put("schema", schema);
		return getJspPath("schema_update.jsp");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void doUpdate(TopicSchema schema, HttpServletResponse response) {
		AjaxResponse ret = new AjaxResponse();
		try {
			schemaService.addSchema(schema, true);
			closeDialogAndRefreshTab(ret, TopicController.TOPIC_LIST_TAB_ID, "添加新版本成功,请查看schema管理页面");
		} catch (Exception e) {
			genAjaxErrorInfo(ret, e);
		} finally {
			AjaxResponseUtil.responseJsonObject(response, ret);
		}
	}
	
	@RequestMapping(value = "/audit/{id}", method = RequestMethod.POST)
	public void doAudit(@PathVariable("id") long id, HttpServletResponse response) {
		AjaxResponse ret = new AjaxResponse();
		try {
			TopicSchema schema = schemaService.getSchema(id);
			schemaService.auditSchema(schema);
			closeDialogAndRefreshTab(ret, SCHEMA_LIST_TAB_ID, "审核成功,并已注册到Schema Registry");
		} catch (Exception e) {
			genAjaxErrorInfo(ret, e);
		} finally {
			AjaxResponseUtil.responseJsonObject(response, ret);
		}
		
	}
	
	@RequestMapping(value = "/edit/{schemaId}", method = RequestMethod.GET)
	public String showEdit(@PathVariable("schemaId") long schemaId, HttpSession session, Map<String, Object> map) {
		map.put("schema", schemaService.getSchema(schemaId));
		map.put("fixedFields", topicService.getFixedFields());
		return getJspPath("schema_edit.jsp");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public void doEdit(TopicSchema schema, HttpServletResponse response) {
		AjaxResponse ret = new AjaxResponse();
		try {
			schemaService.updateSchema(schema);
			closeDialogAndRefreshTab(ret, SCHEMA_LIST_TAB_ID, "修改成功");
		} catch (Exception e) {
			genAjaxErrorInfo(ret, e);
		} finally {
			AjaxResponseUtil.responseJsonObject(response, ret);
		}
	}
	
	@Override
	protected String getJspPath() {
		return "/jsp/schema/";
	}
}
