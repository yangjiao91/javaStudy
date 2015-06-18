package com.funshion.dobo.base.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.funshion.dobo.base.dao.entity.User;
import com.funshion.dobo.base.dao.mapper.UserMapper;
import com.funshion.lego.utils.MessageDigestUtil;


/**
 * @author lirui
 *
 */
@Service("userService")
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	/**
	 * 根据登录名得到用户信息
	 */
	public User getUserByAccount(String account) {
		return userMapper.getUserByAccount(account);
	}
	
	/**
	 * 更新用户密码
	 * @param loginUser 登录的用户
	 * @param oldPassword 旧密码（明文）
	 * @param newPassword 新密码（明文）
	 * @param rnewPassword 重复输入新密码（明文）
	 * @return 成功返回 ture
	 */
	public void updateUserPassword(User loginUser, String oldPassword, String newPassword, String rnewPassword) {
		if (loginUser == null){
			//throw new LoginTimeoutException();
		}
		
		oldPassword = MessageDigestUtil.getMD5(oldPassword);
		newPassword = MessageDigestUtil.getMD5(newPassword);
		rnewPassword = MessageDigestUtil.getMD5(rnewPassword);
		
		if (newPassword == null || !newPassword.equals(rnewPassword)) {
			//throw new AdminBusinessException("两次输入的新密码不一致");
		}
		
		if (oldPassword == null || !oldPassword.equals(loginUser.getPassword())) {
			//throw new AdminBusinessException("旧密码输入不正确");
		}
		
		userMapper.updateUserPassword(loginUser.getId(), newPassword);
		loginUser.setPassword(newPassword);
	}
	
	/**
	 * 得到指定用户的权限
	 */
	public Map<String, String> getUserAuths(Long userId) {
		Map<String, String> map = new HashMap<String, String>();
		for(String str : userMapper.getUserAuths(userId)){
			map.put(str, Boolean.TRUE.toString());
		}
		return map;
	}

	/** 根据用户Id得到用户信息 */
	public User getUser(Long id) {
		return userMapper.getUser(id);
	}
}
