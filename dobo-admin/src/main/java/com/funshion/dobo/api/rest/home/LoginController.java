package com.funshion.dobo.api.rest.home;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.funshion.dobo.api.rest.AbstractAdminController;
import com.funshion.dobo.base.dao.entity.User;
import com.funshion.dobo.base.service.impl.UserService;
import com.funshion.dobo.common.GlobalAdminConstant;
import com.funshion.lego.utils.MessageDigestUtil;

/**
 * 登录相关操作
 * 
 * @author lirui
 * 
 */
@Controller
public class LoginController extends AbstractAdminController{

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/")
	public String index() {
		return "/login";
	}

	@RequestMapping(value = "/home")
	public String home() {
		return getJspPath("home.jsp");
	}
	
	/** 跳转到登录页面 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return getJspPath() + "login.jsp";
	}

	/** 验证用户名密码 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLogin(HttpSession session, HttpServletRequest request, Map<String, Object> map) {
		if (doLoginCommon(session, request, map)) {
			return getJspPath("home.jsp");
		} else {
			return getJspPath("login.jsp");
		}
	}

	/** 跳转到登录对话框页面 */
	@RequestMapping(value = "/login_dialog", method = RequestMethod.GET)
	public String loginDialog() {
		return getJspPath() + "login_dialog.jsp";
	}

	/**
	 * 登录对话框的登录
	 */
	@RequestMapping(value = "/login_dialog", method = RequestMethod.POST)
	public void doChangePwd(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map) {
		System.out.println("do not surport!");
	}

	/**
	 * 检查用户是否可以登录，如果可以放必要的内容在session和页面流里
	 */
	private boolean doLoginCommon(HttpSession session, HttpServletRequest request, Map<String, Object> map) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		User loginUser = userService.getUserByAccount(username.toLowerCase());
		if (loginUser != null) {
			if (loginUser.getIsDeleted()) {
				map.put("errMsg", "该用户被禁止登陆");
				return false;
			}

			password = MessageDigestUtil.getMD5(password);
			if (!StringUtils.isBlank(loginUser.getPassword())
					&& loginUser.getPassword().equals(password)) {
				return true;
			}
		}

		map.put("errMsg", "用户名或密码错误");
		return false;
	}

	/** 登出 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.removeAttribute(GlobalAdminConstant.SessionKey.LOGIN_USER);
		return getJspPath() + "login.jsp";
	}

	protected String getJspPath() {
		return "/jsp/home/";
	}
}
