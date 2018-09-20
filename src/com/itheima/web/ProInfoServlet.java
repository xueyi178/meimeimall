package com.itheima.web;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.ProductBean;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
/**
 * 1:根据ID查询该商品的信息
 * @author:XueYi
 * @time:2017年7月26日 上午9:49:25
 * @version:1.0
 * @company:songbai
 */
public class ProInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1:获取表单数据
		//1.1:获得商品的id
		String pid = request.getParameter("pid");
		//1.2:获得类别的id
		String cid = request.getParameter("cid");
		//1.3:获得当前页
		String currentPage = request.getParameter("currentPage");
		//2:调用业务逻辑
		ProductService productService = new ProductServiceImpl();
		ProductBean productBean = productService.findProductByPid(pid);
		//3:分发转向
		request.setAttribute("PRODUCTBEAN_INFO", productBean);
		request.setAttribute("CID", cid);
		request.setAttribute("CURRENT_PAGE", currentPage);
		
		//4:浏览历史代码
		//4.1:获得客户端携带的cookie,---获得名字是pids的cookie
		String pids = pid;
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())){
					pids = cookie.getValue();
					//1-3-2 本次访问商品pid是8----->8-1-3-2
					//1-3-2 本次访问商品pid是3----->3-1-2
					//1-3-2 本次访问商品pid是2----->2-1-3
					//4.2:将pids拆成一个数组
					//{3-1-2}
					String[] split = pids.split("-");
					//4.3:将数组转换成集合
					//{3-1-2}
					List<String> asList = Arrays.asList(split);
					//4.4:将集合包装成LinkedList
					//{3-1-2}
					LinkedList<String> linkedList = new LinkedList<String>(asList);
					//4.5:判断集合中是否存在当前的pid
					if(linkedList.contains(pid)){
						//4.6:包含当前商品的pid,删除pid,然后添加的前面 
						linkedList.remove(pid);
						linkedList.addFirst(pid);
					}else{
						//4.7:不包含当前查看的pid,直接将pid放到头上
						linkedList.addFirst(pid);
					}
					
					//4.8:将{3-1-2}转换成3-1-2字符串
					StringBuffer buffer = new StringBuffer();
					for(int i=0;i<linkedList.size()&&i<7;i++){
						buffer.append(linkedList.get(i));
						buffer.append("-");
					}
					//4.9:去掉3-1-2-后的-
					pids = buffer.substring(0, buffer.length()-1);
				}
			}
		}
		
		//4.10:创建新的cookie
		Cookie cookie_pids = new Cookie("pids", pids);
		response.addCookie(cookie_pids);
		
		//5:当你在转发之前,需要创建cookie存储pid
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
