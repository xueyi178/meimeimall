package com.itheima.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.domain.UserBean;
import com.itheima.service.UserReginService;
import com.itheima.service.impl.UserReginServiceImpl;
/**
 * 1:用户登录的servlet
 * @author:XueYi
 * @time:2017年7月27日 下午4:20:14
 * @version:1.0
 * @company:songbai
 */
public class UserLoginServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 1:用户登录的方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1:获取表单数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//2:调用业务逻辑
		UserReginService userReginService = new UserReginServiceImpl();
		UserBean userBean = userReginService.login(username,password);

		//3:判断用户自动登录
		if(userBean!=null){
			//4:获取自动登录的名
			String autoLogin = request.getParameter("autoLogin");
			if("true".equals(autoLogin)){
				//5:要自动登录
				//6:创建存储用户名的cookie
				Cookie cookie_username = new Cookie("cookie_username", userBean.getUsername());
				cookie_username.setMaxAge(10*60);
				//创建存储密码的cookie
				Cookie cookie_password = new Cookie("cookie_password",userBean.getPassword());
				cookie_password.setMaxAge(10*60);
				response.addCookie(cookie_username);
				response.addCookie(cookie_password);
			}
			//将user对象存到session中
			request.getSession().setAttribute("userBean", userBean);
			//重定向到首页
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else{
			request.setAttribute("loginError", "用户名或密码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}


	/**
	 * 2:用户退出的方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1:获取session
		HttpSession session = request.getSession();
		
		//2:将session中的用户删除
		session.removeAttribute("userBean");
		
		//3:将cookies中的用户删除
		Cookie cookie_username = new Cookie("cookie_username",null);
		cookie_username.setMaxAge(0);
		
		//4:创建存储密码的cookie
		Cookie cookie_password = new Cookie("cookie_password",null);
		cookie_password.setMaxAge(0);
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);
		
		//5:重定向登录页面
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
}
