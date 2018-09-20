package com.itheima.service;

import com.itheima.domain.UserBean;

/**
 * 1:用户的业务逻辑层接口
 * @author:XueYi
 * @time:2017年7月21日 上午10:38:14
 * @version:1.0
 * @company:songbai
 */
public interface UserReginService {
	/**
	 * 1:用户注册的方法
	 * @param userBean
	 * @return boolean
	 */
	public boolean regint(UserBean userBean);

	/**
	 * 2:用户激活的方法
	 * @param activeCode
	 */
	public void active(String activeCode);

	/**
	 * 3:校验用户名是否存在的方法
	 * @param username
	 * @return
	 */
	public boolean checkUsername(String username);
	
	/**
	 * 4:用户登录的方法
	 * @param username
	 * @param password
	 */
	public UserBean login(String username, String password);
}
