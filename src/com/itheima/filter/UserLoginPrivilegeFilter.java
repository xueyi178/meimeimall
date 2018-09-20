package com.itheima.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.domain.UserBean;
/**
 * 1:判断用户是否登录
 * @author:XueYi
 * @time:2017年7月27日 下午4:55:23
 * @version:1.0
 * @company:songbai
 */
public class UserLoginPrivilegeFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//1:强制转换HttpServletRequest和HttpServletResponse对象
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		//2:校验用户是否登录----校验session是否存在user对象
		HttpSession session = req.getSession();

		//3:判断用户是否已经登录 未登录下面代码不执行
		UserBean userBean = (UserBean) session.getAttribute("userBean");
		if(userBean==null){
			//4:没有登录
			resp.sendRedirect(req.getContextPath()+"/login.jsp");
			return;
		}

		//5:放行
		chain.doFilter(req, resp);

	}
	@Override
	public void destroy() {

	}
}
