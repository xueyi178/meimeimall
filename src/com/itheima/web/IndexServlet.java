package com.itheima.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.CategoryBean;
import com.itheima.domain.ProductBean;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
/**
 * 1:热门商品和最新商品的servlet
 * @author:XueYi
 * @time:2017年7月24日 下午4:49:17
 * @version:1.0
 * @company:songbai
 */
public class IndexServlet extends HttpServlet {
	public IndexServlet(){
		System.out.println("前台页面的serve");
	}
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1:调用前台业务逻辑
		ProductService productService = new ProductServiceImpl();
		//1.1:准备热门商品---list<Product>
		List<ProductBean> HotProductList = productService.findeHotProductList();
		//1.2:准备最新商品---list<Product>
		List<ProductBean> NewProductList = productService.findeNewProductList();
		
		//2:分发转向
		//2.1:把热门商品存到request中
		request.setAttribute("HOTPRODUCTLIST", HotProductList);
		//2.2:把最新商品存到request中
		request.setAttribute("NEWPRODUCTLIST", NewProductList);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
