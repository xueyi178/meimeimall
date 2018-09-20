package com.itheima.domain;

import java.io.Serializable;

/**
 * 1:商品类型的实体类
 * @author:XueYi
 * @time:2017年7月24日 下午5:08:28
 * @version:1.0
 * @company:songbai
 */
public class CategoryBean implements Serializable{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -8008350737409831000L;

	private String cid;
	private String cname;
	
	/**
	 * @param cid
	 * 		类型的ID
	 * @param cname
	 * 		类型的名称
	 */
	public CategoryBean(String cid, String cname) {
		super();
		this.cid = cid;
		this.cname = cname;
	}
	
	public CategoryBean() {
		super();
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "CategoryBean [cid=" + cid + ", cname=" + cname + "]";
	}
}
