package com.funshion.dobo.api.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.funshion.dobo.base.dao.entity.User;
import com.funshion.dobo.common.GlobalAdminConstant;
import com.funshion.dobo.utils.AjaxResponseUtil.AjaxResponse;
import com.funshion.lego.utils.JsonUtil;

/**
 * Dobo Admin 下各个 Controller 的抽象父类
 * @author lirui
 *
 */
public abstract class AbstractAdminController extends AbstractController {
	
	/** 将 session 中的信息放入流程中 */
	protected void putSessionInfosToFlow(HttpSession session, Map<String, Object> map) {
		User user = (User) session.getAttribute(GlobalAdminConstant.SessionKey.LOGIN_USER);
		map.put("user", user);
		map.put("currDate", new Date());
	}
	
	/** 得到登陆用户的Id */
	protected Long getLoginUserId(HttpSession session) {
		User user = (User) session.getAttribute(GlobalAdminConstant.SessionKey.LOGIN_USER);
		if(user != null){
			return user.getId();
		}else{
			return null;
		}
	}
	
	/** 得到登陆的用户信息 */
	protected User getLoginUser(HttpSession session) {
		return (User) session.getAttribute(GlobalAdminConstant.SessionKey.LOGIN_USER);
	}
	
	
	protected String getJspPath(String jspFileName) {
		return getJspPath().concat(jspFileName);
	}
	
	protected Map<String, Object> genAjaxResponseResult(int retCode, String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("retCode", retCode);
		result.put("message", message);
		return result;
	}
	
	/** 产生错误信息 */
	protected void genAjaxErrorInfo(AjaxResponse ret, Exception e) {
		logger.error("ajax访问失败:" , e);
		ret.setStatusCode(GlobalAdminConstant.AjaxResponseStatusCode.FAIL);
		ret.setMessage("<b>" + e.getMessage() + "</b>");
	}
	
	/** 返回前端 Ajax 请求的结果  */
	protected void responseAjaxJson(HttpServletResponse response, AjaxResponse ret){
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(JsonUtil.generateJsonWithDefaultValue(ret));
			response.getWriter().flush();
		} catch (Exception e) {
			logger.error("Fail to response ajax json.", e);
		}
	}
	
	/** 关闭对话框并刷新指定的页签 */
	protected void closeDialogAndRefreshTab(AjaxResponse ret, String navTabId, String message) {
		ret.setMessage(message);
		ret.setCloseCurrent(true);
		ret.setTabid(navTabId);
	}
	
	/** 显示提示信息并刷新指定页签 */
	protected void refreshTab(AjaxResponse ret, String navTabId, String message) {
		ret.setMessage(message);
		ret.setTabid(navTabId);
	}
	
	/** Controller 对应 JSP 文件的路径 */
	protected abstract String getJspPath();
}
