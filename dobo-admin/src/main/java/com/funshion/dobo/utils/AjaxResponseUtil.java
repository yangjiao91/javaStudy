package com.funshion.dobo.utils;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.funshion.dobo.common.GlobalAdminConstant.AjaxResponseStatusCode;
import com.funshion.lego.utils.JsonUtil;

/**
 * @author wangjw lirui
 *
 */
public class AjaxResponseUtil {
	private static final Logger logger = LoggerFactory.getLogger(AjaxResponseUtil.class);

	/** 返回前端 Ajax 请求的结果 */
	public static void responseJsonObject(HttpServletResponse response, Object ret){
		try {
			responseJsonString(response, JsonUtil.generateJsonWithDefaultValue(ret));
		} catch (Exception e) {
			logger.error("Fail to response ajax json.", e);
		}
	}

	/** 返回前端 Ajax 请求的结果 */
	public static void responseJsonString(HttpServletResponse response, String str){
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(str);
			response.getWriter().flush();
		} catch (Exception e) {
			logger.error("Fail to response ajax json.", e);
		}
	}

	/** BJUI表单提交回调参数 */
	public static class AjaxResponse {
		/** 状态码(ok = 200, error = 300, timeout = 301)，可以在BJUI.init时配置三个参数的默认值。*/
		private int statusCode = AjaxResponseStatusCode.SUCCESS;
		/** 信息内容 */
		private String message;
		/** 待刷新navtab id，多个id以英文逗号分隔开，当前的navtab id不需要填写，填写后可能会导致当前navtab重复刷新 */
		private String tabid;
		/** 待刷新dialog id，多个id以英文逗号分隔开，请不要填写当前的dialog id，要控制刷新当前dialog，请设置dialog中表单的reload参数。 */
		private String dialogid;
		/** 是否关闭当前窗口(navtab或dialog) */
		private boolean closeCurrent;
		/** 跳转到某个url。*/
		private String forward;
		/** 跳转url前的确认提示信息。 */
		private String forwardConfirm;
		
		public int getStatusCode() {
			return statusCode;
		}
		public void setStatusCode(int statusCode) {
			this.statusCode = statusCode;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getTabid() {
			return tabid;
		}
		public void setTabid(String tabid) {
			this.tabid = tabid;
		}
		public String getDialogid() {
			return dialogid;
		}
		public void setDialogid(String dialogid) {
			this.dialogid = dialogid;
		}
		public boolean getCloseCurrent() {
			return closeCurrent;
		}
		public void setCloseCurrent(boolean closeCurrent) {
			this.closeCurrent = closeCurrent;
		}
		public String getForward() {
			return forward;
		}
		public void setForward(String forward) {
			this.forward = forward;
		}
		public String getForwardConfirm() {
			return forwardConfirm;
		}
		public void setForwardConfirm(String forwardConfirm) {
			this.forwardConfirm = forwardConfirm;
		}
		
		

	}
}
