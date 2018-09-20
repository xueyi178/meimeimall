package com.itheima.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.service.UserReginService;
import com.itheima.service.impl.UserReginServiceImpl;
/**
 * 1:激活功能
 * @author:XueYi
 * @time:2017年7月22日 上午11:18:21
 * @version:1.0
 * @company:songbai
 */
public class ActiveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1:获取激活码
		String activeCode = request.getParameter("activeCode");
		//2:调用业务逻辑层
		UserReginService userReginService = new UserReginServiceImpl();
		userReginService.active(activeCode);
		//3:分发转向
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
