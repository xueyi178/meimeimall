package com.itheima.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.UserBean;
import com.itheima.service.UserReginService;
import com.itheima.service.impl.UserReginServiceImpl;
/**
 * 1:自动登录的过滤器
 * @author:XueYi
 * @time:2017年7月27日 下午4:45:34
 * @version:1.0
 * @company:songbai
 */
public class AutoLoginFilter implements Filter{

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//1:强制转换成HttpServletRequest和HttpServletResponse
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse)response;
		//2:获取session的userBean对象
		UserBean userBean = (UserBean) req.getSession().getAttribute("userBean");
		//3:如果用户不存在
		if(userBean == null){
			String cookie_username = null;
			String cookie_password = null;
			//4:创建cookie
			Cookie[] cookies = req.getCookies();
			if(cookies!=null){
				for (Cookie cookie : cookies) {
					if("cookie_username".equals(cookie.getName())){
						cookie_username = cookie.getValue();
					}
					if("cookie_password".equals(cookie.getName())){
						cookie_password = cookie.getValue();
					}
				}
			}
			
			//5:如果用户和密码不为null
			if(cookie_username!=null&&cookie_password!=null){
				//6:就去数据库校验该用户名和密码是否正确
				UserReginService reginService = new UserReginServiceImpl();
				reginService.login(cookie_username, cookie_password);
				//7:完成自动登录
				req.getSession().setAttribute("userBean", userBean);
			}
		}
		
		//8:放行
		chain.doFilter(req, res);
	}

	@Override
	public void destroy() {
		
	}

}
