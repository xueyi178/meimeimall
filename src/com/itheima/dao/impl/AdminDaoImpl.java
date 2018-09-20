package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.itheima.dao.AdminDao;
import com.itheima.domain.CategoryBean;
import com.itheima.domain.OrdersBean;
import com.itheima.domain.ProductBean;
import com.itheima.utils.DataSourceUtils;

/**
 * 1:后台的数据访问层实现类
 * @author:XueYi
 * @time:2017年7月31日 上午10:43:59
 * @version:1.0
 * @company:songbai
 */
public class AdminDaoImpl implements AdminDao{

	/**
	 * 1:通过ajax获取商品类型的方法
	 * @return
	 * @throws SQLException 
	 */
	@Override
	public List<CategoryBean> findAllCategory() throws SQLException {
		//1:创建QueryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM category";
		return queryRunner.query(sql, new BeanListHandler<CategoryBean>(CategoryBean.class));
	}

	/**
	 * 2:后台商品添加的方法
	 * @param productBean
	 */
	@Override
	public void addProduct(ProductBean productBean) throws SQLException {
		//1:创建QueryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "INSERT INTO product(pid,pname,market_price,shop_price,pimage,pdate,is_hot,pdesc,pflag,cid)VALUES(?,?,?,?,?,?,?,?,?,?);";
		queryRunner.update(sql,productBean.getPid(),productBean.getPname(),productBean.getMarket_price(),
				productBean.getShop_price(),productBean.getPimage(),productBean.getPdate(),
				productBean.getIs_hot(),productBean.getPdesc(),productBean.getPflag(),productBean.getCategory().getCid());
	}

	/**
	 * 3:查询所有订单的方法
	 * @return
	 * @throws SQLException
	 */
	@Override
	public List<OrdersBean> findAllOrders() throws SQLException {
		//1:创建QueryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders";
		return queryRunner.query(sql, new BeanListHandler<OrdersBean>(OrdersBean.class));
	}

	/**
	 * 4:根据订单id查询顶单项和商品信息
	 * @param oid
	 * @return List<Map<String, Object>>
	 * @throws SQLException 
	 */
	@Override
	public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {
		//1:创建QueryRunner对象,并获取数据源
		QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT p.pimage,p.pname,p.shop_price,i.count,i.subtotal FROM"
				+ "  orderitem i,product p WHERE i.pid=p.pid AND i.oid = ?";
		return queryRunner.query(sql, new MapListHandler(),oid);
	}
}
