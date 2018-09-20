package com.itheima.web;

import java.io.IOException;
import java.lang.reflect.Method;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 1:通过反射来优化servlet
 * @author:XueYi
 * @time:2017年7月26日 下午3:47:45
 * @version:1.0
 * @company:songbai
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1:获得请求的参数
			String methodName = request.getParameter("method");
			//2:this:获得当前被访问对象的字节码对象
			//2.1:通过反射获取你要执行的方法
			Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//4:执行相应功能方法
			method.invoke(this, request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
