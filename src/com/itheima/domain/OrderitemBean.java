package com.itheima.domain;

import java.io.Serializable;
/**
 * 1:订单项的实体
 * @author:XueYi
 * @time:2017年7月28日 上午10:32:54
 * @version:1.0
 * @company:songbai
 */
public class OrderitemBean implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -9013286360749737399L;

	  private String itemid;
	  private Integer count;
	  private double subtotal;
	  private ProductBean productBean;
	  private OrdersBean ordersBean;
	  
	  /**
	   * 
	   * @param itemid
	   * 		订单项id
	   * @param count
	   * 		订单项的数量
	   * @param subtotal
	   * 		订单项的小计
	   * @param productBean
	   * 		订单项内部的商品
	   * @param ordersBean
	   * 		该订单项属于哪个订单
	   */
	  public OrderitemBean(String itemid, Integer count, double subtotal, ProductBean productBean,
			OrdersBean ordersBean) {
		super();
		this.itemid = itemid;
		this.count = count;
		this.subtotal = subtotal;
		this.productBean = productBean;
		this.ordersBean = ordersBean;
	}

	public OrderitemBean() {
		super();
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public ProductBean getProductBean() {
		return productBean;
	}

	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}

	public OrdersBean getOrdersBean() {
		return ordersBean;
	}

	public void setOrdersBean(OrdersBean ordersBean) {
		this.ordersBean = ordersBean;
	}
}