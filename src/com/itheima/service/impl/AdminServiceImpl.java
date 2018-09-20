package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.itheima.dao.AdminDao;
import com.itheima.dao.impl.AdminDaoImpl;
import com.itheima.domain.CategoryBean;
import com.itheima.domain.OrdersBean;
import com.itheima.domain.ProductBean;
import com.itheima.service.AdminService;
/**
 * 1:后台的业务逻辑层实现类
 * @author:XueYi
 * @time:2017年7月31日 上午10:45:23
 * @version:1.0
 * @company:songbai
 */
public class AdminServiceImpl implements AdminService{
	/**后台的数据访问层接口指向数据访问层的实现类*/
	AdminDao adminDao = new AdminDaoImpl();
	/**
	 * 1:通过ajax获取商品的类型,
	 * @return
	 */
	@Override
	public List<CategoryBean> findAllCategory() {
		List<CategoryBean> categoryBeans = null;
		try {
			categoryBeans = adminDao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryBeans;
	}
	
	/**
	 * 2:后台商品添加的方法
	 */
	@Override
	public void addProduct(ProductBean productBean) {
		try {
			adminDao.addProduct(productBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 3:查询所有订单的方法
	 * @return
	 */
	@Override
	public List<OrdersBean> findAllOrders() {
		List<OrdersBean> ordersBeans = null;
		try {
			ordersBeans = adminDao.findAllOrders();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ordersBeans;
	}

	/**
	 * 4:根据订单id查询顶单项和商品信息
	 * @param oid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findOrderInfoByOid(String oid) {
		List<Map<String, Object>> list = null;
		try {
			list = adminDao.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
