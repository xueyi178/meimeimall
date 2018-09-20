package com.itheima.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.itheima.domain.CategoryBean;
import com.itheima.domain.OrdersBean;
import com.itheima.service.AdminService;
import com.itheima.service.impl.AdminServiceImpl;
/**
 * 1:后台的servlet
 * @author:XueYi
 * @time:2017年7月31日 上午10:38:09
 * @version:1.0
 * @company:songbai
 */
public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * 1:通过ajax获取商品的类型,
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void findAllCategory(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1:业务逻辑层的接口指向业务逻辑层的实现类
		AdminService adminService = new  AdminServiceImpl();
		List<CategoryBean> categoryBeans = adminService.findAllCategory();
		//2:创建gson对象
		Gson gson = new Gson();
		//3:把对象转换成json字符串
		String json = gson.toJson(categoryBeans);
		//4:设置响应的数据格式
		response.setContentType("application/json;charset=UTF-8");
		//5:响应数据
		response.getWriter().write(json);
	}
	
	/**
	 * 2:获得所有的订单的方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void findAllOrders(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//获得所有订单的信息---list<orderBean>
		//1:调用业务逻辑的方法
		AdminService adminService = new AdminServiceImpl();
		List<OrdersBean> ordersBeans = adminService.findAllOrders();
		//2:存域
		request.setAttribute("ordersBeans", ordersBeans);
		//3:分发转向转发
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
	}
	
	/**
	 * 3:根据订单id查询顶单项和商品信息
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void findOrderInfoByOid(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//1:获取表单数据
		String oid = request.getParameter("oid");
		//2:调用业务逻辑
		AdminService adminService = new AdminServiceImpl();
		List<Map<String, Object>> listMap = adminService.findOrderInfoByOid(oid);
		//3:把listMap对象转换成json字符串
		Gson gson = new Gson();
		String json = gson.toJson(listMap);
		System.out.println(json);
		//4:设置编码格式
		response.setContentType("application/json;charset=utf-8");
		//5:响应数据
		response.getWriter().write(json);
	}
}
