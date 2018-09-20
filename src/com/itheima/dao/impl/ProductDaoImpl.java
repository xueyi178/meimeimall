package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.ProductDao;
import com.itheima.domain.CategoryBean;
import com.itheima.domain.OrderitemBean;
import com.itheima.domain.OrdersBean;
import com.itheima.domain.ProductBean;
import com.itheima.utils.DataSourceUtils;
/**
 * 1:前台数据访问层的实现类
 * @author:XueYi
 * @time:2017年7月24日 下午4:56:44
 * @version:1.0
 * @company:songbai
 */
public class ProductDaoImpl implements ProductDao{
	/**
	 * 1:获得热门商品的方法
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public List<ProductBean> findeHotProductList() throws SQLException {
		//1:创建queryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT pid,pname,market_price,shop_price,pimage,pdate,is_hot,pdesc,pflag,cid FROM product WHERE is_hot = ? limit ?,?";
		List<ProductBean> hotList = queryRunner.query(sql, new BeanListHandler<ProductBean>(ProductBean.class),1,0,9);
		return hotList;
	}

	/**
	 * 2:获得最新商品的方法
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public List<ProductBean> findNewProductList() throws SQLException {
		//1:创建queryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT pid,pname,market_price,shop_price,pimage,pdate,is_hot,pdesc,pflag,cid FROM product order by pdate desc limit ?,?";
		List<ProductBean> newList = queryRunner.query(sql, new BeanListHandler<ProductBean>(ProductBean.class),0,9);
		return newList;
	}

	/**
	 * 3:获得导航菜单的方法
	 * @return
	 */
	@Override
	public List<CategoryBean> findAllCategory() throws SQLException {
		//1:创建queryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT cid,cname FROM category";
		return queryRunner.query(sql, new BeanListHandler<CategoryBean>(CategoryBean.class));
	}

	/**
	 * 4:获得总条数的方法
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int getCount(String cid) throws SQLException {
		//1:创建queryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT COUNT(*) FROM product where cid = ?";
		Long query = (Long) queryRunner.query(sql, new ScalarHandler(),cid);
		return query.intValue();
	}

	/**
	 * 5:分页查询的方法
	 * @param cid
	 * 			类别Id
	 * @param index
	 * 			显示的起止条数
	 * @param pageSize
	 * 			每页显示的条数
	 * @throws SQLException 
	 */
	@Override
	public List<ProductBean> findProductByPage(String cid, int index, int pageSize) throws SQLException {
		//1:创建一个QueryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT pid,pname,market_price,shop_price,pimage,pdate,is_hot,pdesc,pflag,cid FROM product where cid=? limit ?,?";
		List<ProductBean> list = queryRunner.query(sql, new BeanListHandler<ProductBean>(ProductBean.class),cid,index,pageSize);
		return list;
	}

	/**
	 * 6:根据商品id查询商品的详细信息
	 * @param pid
	 */
	@Override
	public ProductBean findProductByPid(String pid) throws SQLException {
		//1:创建QueryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT pid,pname,market_price,shop_price,pimage,pdate,is_hot,pdesc,pflag,cid FROM product WHERE pid = ?";
		ProductBean productBean = queryRunner.query(sql, new BeanHandler<ProductBean>(ProductBean.class),pid);
		return productBean;
	}

	/**
	 * 7:向orders插入数据
	 * @param ordersBean
	 */
	@Override
	public void addOrder(OrdersBean ordersBean) throws SQLException {
		//1:创建queryRunner对象,并获取数据源的方法
		QueryRunner queryRunner = new QueryRunner();
		String sql = "INSERT INTO orders(oid,ordertime,total,state,address,NAME,telephone,uid)VALUES(?,?,?,?,?,?,?,?)";
		queryRunner.update(DataSourceUtils.getConnection(), sql, ordersBean.getOid(),ordersBean.getOrdertime(),ordersBean.getTotal(),
				ordersBean.getState(),ordersBean.getAddress(),ordersBean.getName(),ordersBean.getTelephone(),ordersBean.getUserBean().getUid());
	}

	/**
	 * 8:向orderitem表插入数据
	 * @param ordersBean
	 */
	@Override
	public void addOrderItem(OrdersBean ordersBean) throws SQLException {
		//1:创建queryRunner对象,并获取数据源的方法
		QueryRunner queryRunner = new QueryRunner();
		String sql = "INSERT INTO orderitem(itemid,count,subtotal,pid,oid)VALUES(?,?,?,?,?)";
		//2:订单项的集合
		List<OrderitemBean> orderitemBeans = ordersBean.getOrderitemBeans();
		for (OrderitemBean orders : orderitemBeans) {
			queryRunner.update(DataSourceUtils.getConnection(),sql,orders.getItemid(),orders.getCount(),orders.getSubtotal(),orders.getProductBean().getPid(),orders.getOrdersBean().getOid());
		}
	}

	/**
	 * 9:更新收货人信息的方法
	 * @param ordersBean
	 * @throws SQLException
	 */
	@Override
	public void updateOrderAdrr(OrdersBean ordersBean) throws SQLException {
		//1:创建QueryRunner对象,并获取数据
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set address=?,NAME=?,telephone=? where oid = ?";
		queryRunner.update(sql,ordersBean.getAddress(),ordersBean.getName(),ordersBean.getTelephone(),ordersBean.getOid());
	}

	/**
	 * 10:查询该用户所有订单的放法
	 * @param uid
	 * @return
	 */
	@Override
	public List<OrdersBean> findAllOrder(String uid) throws SQLException {
		//1:创建QueryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where uid = ?";
		return queryRunner.query(sql, new BeanListHandler<OrdersBean>(OrdersBean.class),uid);
	}

	/**
	 * 11:查询该用户订单中的订单项
	 * @param oid
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException {
		//1:创建QueryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select i.count,i.subtotal,p.pname,p.pimage,p.shop_price from orderitem i,product p where i.pid = p.pid and i.oid =?";
		List<Map<String, Object>> mapList = queryRunner.query(sql, new MapListHandler(),oid);
		return mapList;
	}
}
