package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.itheima.domain.CategoryBean;
import com.itheima.domain.OrderitemBean;
import com.itheima.domain.OrdersBean;
import com.itheima.domain.ProductBean;

/**
 * 1:前台的数据访问层接口
 * @author:XueYi
 * @time:2017年7月24日 下午4:56:07
 * @version:1.0
 * @company:songbai
 */
public interface ProductDao {

	/**
	 * 1:获得热门商品的方法
	 * @return
	 */
	public List<ProductBean> findeHotProductList()throws SQLException;

	/**
	 * 2:获得最新商品的方法
	 * @return
	 */
	public List<ProductBean> findNewProductList()throws SQLException;

	/**
	 * 3:获得导航菜单的方法
	 * @return
	 */
	public List<CategoryBean> findAllCategory()throws SQLException;

	/**
	 * 4:获得总条数的方法
	 * @return
	 * @throws SQLException
	 */
	public int getCount(String cid)throws SQLException;

	/**
	 * 5:分页查询的方法
	 * @param cid
	 * 			类别Id
	 * @param index
	 * 			显示的起止条数
	 * @param pageSize
	 * 			每页显示的条数
	 */
	public List<ProductBean> findProductByPage(String cid, int index, int pageSize)throws SQLException;

	/**
	 * 6:根据商品id查询商品的详细信息
	 * @param pid
	 */
	public ProductBean findProductByPid(String pid)throws SQLException;

	/**
	 * 7:向orders插入数据
	 * @param ordersBean
	 */
	public void addOrder(OrdersBean ordersBean)throws SQLException;

	/**
	 * 8:向orderitem表插入数据
	 * @param ordersBean
	 */
	public void addOrderItem(OrdersBean ordersBean)throws SQLException;

	/**
	 * 9:更新收货人信息的方法
	 * @param ordersBean
	 * @throws SQLException
	 */
	public void updateOrderAdrr(OrdersBean ordersBean)throws SQLException;

	/**
	 * 10:查询该用户所有订单的放法
	 * @param uid
	 * @return
	 */
	public List<OrdersBean> findAllOrder(String uid)throws SQLException;

	/**
	 * 11:查询该用户订单中的订单项
	 * @param oid
	 * @return
	 */
	public List<Map<String, Object>> findAllOrderItemByOid(String oid)throws SQLException;
	

}
