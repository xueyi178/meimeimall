package com.itheima.dao;

import java.sql.SQLException;

import com.itheima.domain.UserBean;
/**
 * 1:用户数据访问层的接口
 * @author:XueYi
 * @time:2017年7月21日 上午10:48:33
 * @version:1.0
 * @company:songbai
 */
public interface UserDao {
	/**
	 * 1:用户注册的方法
	 * @param userBean
	 * @return UserBean
	 */
	public int regint(UserBean userBean)throws SQLException;

	/**
	 * 2:用户激活的方法
	 * @param activeCode
	 */
	public void active(String activeCode)throws SQLException;

	/**
	 * 3:校验用户名是否存在的方法
	 * @param username
	 * @return
	 */
	public long checkUsername(String username)throws SQLException;

	/**
	 * 4:用户登录的方法
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public UserBean login(String username, String password)throws SQLException;
}
