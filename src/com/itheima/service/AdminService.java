package com.itheima.service;

import java.util.List;
import java.util.Map;

import com.itheima.domain.CategoryBean;
import com.itheima.domain.OrdersBean;
import com.itheima.domain.ProductBean;

/**
 * 1:后台的业务逻辑层接口
 * @author:XueYi
 * @time:2017年7月31日 上午10:44:36
 * @version:1.0
 * @company:songbai
 */
public interface AdminService {

	/**
	 * 1:通过ajax获取商品的类型,
	 * @return
	 */
	List<CategoryBean> findAllCategory();

	/**
	 * 2:后台商品添加的方法
	 * @param productBean
	 */
	void addProduct(ProductBean productBean);

	/**
	 * 3:查询所有订单的方法
	 * @return
	 */
	List<OrdersBean> findAllOrders();

	/**
	 * 4:根据订单id查询顶单项和商品信息
	 * @param oid
	 * @return
	 */
	List<Map<String, Object>> findOrderInfoByOid(String oid);

}
