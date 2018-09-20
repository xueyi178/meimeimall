package com.itheima.service;

import java.util.List;
import java.util.Map;

import com.itheima.domain.CategoryBean;
import com.itheima.domain.OrderitemBean;
import com.itheima.domain.OrdersBean;
import com.itheima.domain.PageBean;
import com.itheima.domain.ProductBean;

/**
 * 1:前台业务逻辑层的接口
 * @author:XueYi
 * @time:2017年7月24日 下午4:57:14
 * @version:1.0
 * @company:songbai
 */
public interface ProductService {
	/**
	 * 1:获得热门商品的方法
	 * @return
	 */
	public List<ProductBean> findeHotProductList();

	/**
	 * 2:获得最新商品的方法
	 * @return
	 */
	public List<ProductBean> findeNewProductList();

	/**
	 * 3:获得导航菜单的方法
	 * @return
	 */
	public List<CategoryBean> findAllCategory();

	/**
	 * 4:分页查询的方法
	 * @param cid
	 * @return
	 */
	public PageBean findProductLIstByCidList(String cid,int currentPage,int pageSize);

	/**
	 * 5:根据商品id查询商品详情
	 * @param pid
	 * @return
	 */
	public ProductBean findProductByPid(String pid);

	/**
	 * 6:提交订单,将你订单的数据和订单项的数据存储到数据库中
	 * @param ordersBean
	 */
	public void submitOrder(OrdersBean ordersBean);

	/**
	 * 7:更新收货人信息的方法
	 * @param ordersBean
	 */
	public void updateOrderAdrr(OrdersBean ordersBean);

	/**
	 * 8:获得该用户的订单集合
	 * @param uid
	 * @return
	 */
	public List<OrdersBean> findAllOrder(String uid);

	/**
	 * 9:查询该用户订单中的订单项
	 * @param oid
	 * @return
	 */
	public List<Map<String, Object>> findAllOrderItemByOid(String oid);

}
