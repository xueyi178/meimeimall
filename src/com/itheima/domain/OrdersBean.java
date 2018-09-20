package com.itheima.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 1:订单的实体
 * @author:XueYi
 * @time:2017年7月28日 上午10:31:46
 * @version:1.0
 * @company:songbai
 */
public class OrdersBean implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -2475039158896578075L;

	private String oid;
	private Date ordertime;
	private double total;
	private Integer state;
	private String address;
	private String name;
	private String telephone;
	private UserBean userBean;
	private List<OrderitemBean> orderitemBeans = new ArrayList<OrderitemBean>();

	/**
	 * 
	 * @param oid
	 * 		订单的ID
	 * @param ordertime
	 * 		下单的事件
	 * @param total
	 * 		下单的总金额
	 * @param state
	 * 		支付状态:1代表已付款,2:代表付款
	 * @param address
	 * 		收货人的地址
	 * @param name		
	 * 		收货人的姓名
	 * @param telephone		
	 * 		收货人的联系方式
	 * @param userBean
	 * 		该订单属于哪个用户
	 * @param orderitemBeans
	 * 		多个订单项
	 */
	public OrdersBean(String oid, Date ordertime, double total, Integer state, String address, String name,
			String telephone, UserBean userBean, List<OrderitemBean> orderitemBeans) {
		super();
		this.oid = oid;
		this.ordertime = ordertime;
		this.total = total;
		this.state = state;
		this.address = address;
		this.name = name;
		this.telephone = telephone;
		this.userBean = userBean;
		this.orderitemBeans = orderitemBeans;
	}

	public OrdersBean() {
		super();
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public List<OrderitemBean> getOrderitemBeans() {
		return orderitemBeans;
	}

	public void setOrderitemBeans(List<OrderitemBean> orderitemBeans) {
		this.orderitemBeans = orderitemBeans;
	}
}