package com.funshion.dobo.base.dao.mapper;

import java.util.Set;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.funshion.dobo.base.dao.entity.User;

/**
 * @author lirui
 *
 */
public interface UserMapper {

	/**
	 * 根据登录名得到指定用户
	 */
	User getUserByAccount(@Param(value="account") String account);

	/**
	 * 更新用户密码
	 */
	@Update("update user set password = #{password} where id = #{userId}")
	void updateUserPassword(@Param(value="userId") Long userId, 
			@Param(value="password") String password);

	/**
	 * 得到指定用户的权限
	 */
	Set<String> getUserAuths(@Param(value="userId") Long userId);

	/** 根据用户Id得到用户信息 */
	User getUser(@Param(value="id") Long id);
}
