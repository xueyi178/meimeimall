package com.itheima.service.impl;

import java.sql.SQLException;

import org.apache.tomcat.jni.User;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.UserBean;
import com.itheima.service.UserReginService;

/**
 * 1:用户业务逻辑实现类
 * @author:XueYi
 * @time:2017年7月21日 上午10:42:04
 * @version:1.0
 * @company:songbai
 */
public class UserReginServiceImpl implements UserReginService{

	/**数据访问层的接口指向数据访问层的实习类*/
	UserDao userDao = new UserDaoImpl();
	/**
	 * 1:用户注册的方法
	 * @param userBean
	 * @return boolean
	 */
	@Override
	public boolean regint(UserBean userBean) {
		int rows = 0;
		try {
			rows = userDao.regint(userBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rows>0?true:false;
	}
	
	/**
	 * 2:用户激活的方法
	 * @param activeCode
	 */
	@Override
	public void active(String activeCode) {
		try {
			userDao.active(activeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 3:校验用户名是否存在的方法
	 * @param username
	 * @return
	 */
	@Override
	public boolean checkUsername(String username) {
		long isExits = 0L;
		try {
			isExits = userDao.checkUsername(username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return isExits>0?true:false;
	}

	/**
	 * 4:用户登录的方法
	 * @param username
	 * @param password
	 */
	@Override
	public UserBean login(String username, String password) {
		UserBean userBean = null;
		try {
			userBean = userDao.login(username,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userBean;
	}
}
