package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.itheima.domain.CategoryBean;
import com.itheima.domain.OrdersBean;
import com.itheima.domain.ProductBean;

/**
 * 1:后台的数据访问层接口
 * @author:XueYi
 * @time:2017年7月31日 上午10:43:36
 * @version:1.0
 * @company:songbai
 */
public interface AdminDao {

	/**
	 * 1:通过ajax获取商品类型的方法
	 * @return
	 */
	List<CategoryBean> findAllCategory()throws SQLException;

	/**
	 * 2:后台商品添加的方法
	 * @param productBean
	 */
	void addProduct(ProductBean productBean)throws SQLException;

	/**
	 * 3:查询所有订单的方法
	 * @return
	 * @throws SQLException
	 */
	List<OrdersBean> findAllOrders()throws SQLException;

	/**
	 * 4:根据订单id查询顶单项和商品信息
	 * @param oid
	 * @return List<Map<String, Object>>
	 */
	List<Map<String, Object>> findOrderInfoByOid(String oid)throws SQLException;

}
