package com.itheima.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.PageBean;
import com.itheima.domain.ProductBean;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
/**
 * 1:根据商品类别进行分页查询
 * @author:XueYi
 * @time:2017年7月25日 下午3:44:37
 * @version:1.0
 * @company:songbai
 */
public class ProductListByCidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1:获取表单数据
		String cid = request.getParameter("cid");
		
		//2:获得当前页
		String currentPageStr = request.getParameter("currentPage");
		if(currentPageStr == null){
			currentPageStr = "1";
		}
		
		int currentPage = Integer.parseInt(currentPageStr);
		//3:每页显示的数据
		int pageSize = 12;
		
		//2:调用业务逻辑
		ProductService  productService = new ProductServiceImpl();
		PageBean pageBean = productService.findProductLIstByCidList(cid,currentPage,pageSize);
		
		//3:分发转向
		request.setAttribute("PAGEBEAN", pageBean);
		request.setAttribute("CID", cid);
		
		//4:历史记录
		//定义一个集合,记录历史商品的集合
		List<ProductBean> historProductBeans = new ArrayList<ProductBean>();
		//4.2:获得客户端携带的名字叫pids的cookie
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())){
					String pids = cookie.getValue();
					String[] split = pids.split("-");
					for (String pid : split) {
						ProductBean productByPid = productService.findProductByPid(pid);
						historProductBeans.add(productByPid);
					}
				}
			}
		}
		
		//4.3:将历史记录的集合方法域中
		request.setAttribute("HISTORPRODUCT_BEAN", historProductBeans);
		//5:分发转向
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
