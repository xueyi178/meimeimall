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
 * 1:ajax验证用户名是否存在
 * @author:XueYi
 * @time:2017年7月24日 下午3:34:37
 * @version:1.0
 * @company:songbai
 */
public class ChekUsernameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
		//1:获取表单数据
		String username = request.getParameter("username");
		//2:调用业务逻辑
		UserReginService reginService = new UserReginServiceImpl();
		boolean isExist = reginService.checkUsername(username);
		
		//3:分发转向
		//3.1:封装成json对象
		String json = "{\"isExist\":"+isExist+"}";
		response.getWriter().write(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
		doGet(request, response);
	}

}
