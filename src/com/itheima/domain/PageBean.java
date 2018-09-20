package com.itheima.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 1:分页的数据
 * @author:XueYi
 * @time:2017年7月25日 下午3:50:40
 * @version:1.0
 * @company:songbai
 */
public class PageBean<T> implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -2450808659764008094L;
	private Integer currentPage;
	private Integer pageSize;
	private Integer count;
	private Integer totalPage;
	private List<T> list;
	
	/**
	 * 
	 * @param currentPage
	 * 			当前页
	 * @param pageSize
	 * 			每页显示的个数
	 * @param count
	 * 			总条数
	 * @param totalPage
	 * 			总页数
	 * @param list
	 * 			显示的实体
	 */
	public PageBean(Integer currentPage, Integer pageSize, Integer count, Integer totalPage, List<T> list) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.count = count;
		this.totalPage = totalPage;
		this.list = list;
	}
	public PageBean() {
		super();
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
