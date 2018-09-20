package com.itheima.domain;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 1:购物车的对象
 * @author:XueYi
 * @time:2017年7月27日 上午9:39:33
 * @version:1.0
 * @company:songbai
 */
public class CartBean implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -5977313962512376186L;

	/**该购物车中存储的订单项*/
	private Map<String, CartItemBean> cartItemBeans = new HashMap<String, CartItemBean>();
	
	/**购物车商品的金额的总计*/
	private  double total;

	public CartBean(Map<String, CartItemBean> cartItemBeans, double total) {
		super();
		this.cartItemBeans = cartItemBeans;
		this.total = total;
	}

	public CartBean() {
		super();
	}

	public Map<String, CartItemBean> getCartItemBeans() {
		return cartItemBeans;
	}

	public void setCartItemBeans(Map<String, CartItemBean> cartItemBeans) {
		this.cartItemBeans = cartItemBeans;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
