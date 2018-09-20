package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.UserDao;
import com.itheima.domain.UserBean;
import com.itheima.utils.DataSourceUtils;

/**
 * 1:用户数据访问层的实现类
 * @author:XueYi
 * @time:2017年7月21日 上午10:46:57
 * @version:1.0
 * @company:songbai
 */
public class UserDaoImpl implements UserDao{

	/**
	 * 1:用户注册的方法
	 * @param userBean
	 * @return UserBean
	 * @throws SQLException 
	 */
	@Override
	public int regint(UserBean userBean) throws SQLException {
		//1:创建一个queryRunnerD对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		 int rows = queryRunner.update(sql,userBean.getUid(),userBean.getUsername(),
				userBean.getPassword(),userBean.getName(),userBean.getEmail(),
				userBean.getTelephone(),userBean.getBirthday(),userBean.getSex(),
				userBean.getState(),userBean.getCode());
		return rows;
	}

	/**
	 * 2:用户激活的方法
	 * @param activeCode
	 */
	@Override
	public void active(String activeCode) throws SQLException {
		//1:创建QueryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set state=? where code=?";
		queryRunner.update(sql,1,activeCode);
	}

	/**
	 * 3:校验用户名是否存在的方法
	 * @param username
	 * @return
	 */
	@Override
	public long checkUsername(String username) throws SQLException {
		//1:创建QueryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql= "select count(*) from user where username = ?";
		 long query = (long) queryRunner.query(sql, new ScalarHandler(),username);
		 return query;
	}

	/**
	 * 4:用户登录的方法
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	@Override
	public UserBean login(String username, String password) throws SQLException {
		//1:创建QueryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		return queryRunner.query(sql, new BeanHandler<UserBean>(UserBean.class),username,password);
	}
}
