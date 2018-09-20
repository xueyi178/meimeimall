package com.itheima.service.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.domain.CategoryBean;
import com.itheima.domain.OrderitemBean;
import com.itheima.domain.OrdersBean;
import com.itheima.domain.PageBean;
import com.itheima.domain.ProductBean;
import com.itheima.service.ProductService;
import com.itheima.utils.DataSourceUtils;
/**
 * 1:前台业务逻辑层的实现类
 * @author:XueYi
 * @time:2017年7月24日 下午4:57:42
 * @version:1.0
 * @company:songbai
 */
public class ProductServiceImpl implements ProductService{

	/**前台的业务逻辑层接口指向前台业务逻辑层的实现类*/
	ProductDao productDao = new ProductDaoImpl();
	
	/**
	 * 1:获得热门商品的方法
	 * @return List<ProductBean>
	 * @throws SQLException 
	 */
	@Override
	public List<ProductBean> findeHotProductList() {
		List<ProductBean> hotProductBeans = null;
		try {
			hotProductBeans = productDao.findeHotProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotProductBeans;
	}
	
	/**
	 * 2:获得最新商品的方法
	 * @return List<ProductBean>
	 * @throws SQLException 
	 */
	@Override
	public List<ProductBean> findeNewProductList(){
		List<ProductBean> newProductBeans = null;
		try {
			newProductBeans = productDao.findNewProductList();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return newProductBeans;
	}

	/**
	 * 3:获得导航菜单的方法
	 * @return
	 */
	@Override
	public List<CategoryBean> findAllCategory() {
		List<CategoryBean> categoryList  = null;
		try {
			categoryList = productDao.findAllCategory();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categoryList;
	}

	/**
	 * 4:分页查询的方法
	 * @param cid
	 * @return
	 */
	@Override
	public PageBean findProductLIstByCidList(String cid,int currentPage,int pageSize) {
		//1:封装一个pageBean,返回给web层
		PageBean<ProductBean> pageBean = new PageBean<ProductBean>();
		//4:封装当前页
		pageBean.setCurrentPage(currentPage);
		//5:封装每页显示的条数
		pageBean.setPageSize(pageSize);
		//6:封装总的条数
		int totalCount = 0;
		try {
			totalCount = productDao.getCount(cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setCount(totalCount);
		//7:封装总页数
		int totalPage = (int) Math.ceil(1.0*totalCount/pageSize);
		pageBean.setTotalPage(totalPage);
		//8:当前页显示的数据
		int index = (currentPage-1)*pageSize;
		List<ProductBean> list = null;
		try {
			list = productDao.findProductByPage(cid,index,pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		pageBean.setList(list);
		
		return pageBean;
	}

	/**
	 * 5:根据商品id查询商品详情
	 * @param pid
	 * @return
	 */
	@Override
	public ProductBean findProductByPid(String pid) {
		ProductBean productBean = null;
		try {
			productBean = productDao.findProductByPid(pid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productBean;
	}

	/**
	 * 6:提交订单,将你订单的数据和订单项的数据存储到数据库中
	 * @param ordersBean
	 */
	@Override
	public void submitOrder(OrdersBean ordersBean) {
		try {
			//1:开启事物
			DataSourceUtils.startTransaction();
			//2:调用dao存储order表数据的方法
			productDao.addOrder(ordersBean);
			//3:调用dao存储orderitem表数据的方法
			productDao.addOrderItem(ordersBean);
		} catch (SQLException e) {
			//4:回滚事务
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			//5:释放资源
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * 7:更新收货人信息的方法
	 * @param ordersBean
	 */
	@Override
	public void updateOrderAdrr(OrdersBean ordersBean) {
		try {
			productDao.updateOrderAdrr(ordersBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 8:获得该用户的订单集合
	 * @param uid
	 * @return
	 */
	@Override
	public List<OrdersBean> findAllOrder(String uid) {
		List<OrdersBean> ordersBeans = null;
		try {
			ordersBeans = productDao.findAllOrder(uid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ordersBeans;
	}

	/**
	 * 9:查询该用户订单中的订单项
	 * @param oid
	 * @return
	 */
	@Override
	public List<Map<String, Object>> findAllOrderItemByOid(String oid) {
		List<Map<String, Object>> mapList = null;
		try {
			mapList = productDao.findAllOrderItemByOid(oid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapList;
	}
}
