package com.itheima.domain;

import java.io.Serializable;

/**
 * 1:购物车项
 * @author:XueYi
 * @time:2017年7月27日 上午9:36:38
 * @version:1.0
 * @company:songbai
 */
public class CartItemBean implements Serializable{

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -7608428678182641813L;
	
	private ProductBean productBean;
	private Integer byNumber;
	private double subtotal;
	
	/**
	 * 
	 * @param productBean
	 * 		   商品
	 * @param byNumber
	 * 		购物车的数量
	 * @param subtotal
	 * 		购物车的小计
	 */
	public CartItemBean(ProductBean productBean, Integer byNumber, double subtotal) {
		super();
		this.productBean = productBean;
		this.byNumber = byNumber;
		this.subtotal = subtotal;
	}
	

	public CartItemBean() {
		super();
	}

	public ProductBean getProductBean() {
		return productBean;
	}

	public void setProductBean(ProductBean productBean) {
		this.productBean = productBean;
	}

	public Integer getByNumber() {
		return byNumber;
	}

	public void setByNumber(Integer byNumber) {
		this.byNumber = byNumber;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
}
