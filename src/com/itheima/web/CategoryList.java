package com.itheima.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.itheima.domain.CategoryBean;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.JedisPoolUtils;

import redis.clients.jedis.Jedis;
/**
 * 1:ajax动态获取导航栏信息
 * @author:XueYi
 * @time:2017年7月25日 上午10:51:47
 * @version:1.0
 * @company:songbai
 */
public class CategoryList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1:调用前台业务逻辑
		ProductService productService = new ProductServiceImpl();
		
		
		//1:先从缓冲中查询categoryList,如果有就直接使用,如果没有从数据库中查询存到缓冲中
		//1.1:获得jedis对象,连接redis数据库
		Jedis jedis = JedisPoolUtils.getJedis();
		String categoryListJson = jedis.get("categoryListJson");
		
		//1.2:判断categoryListJson是否为空
		if(categoryListJson == null){
			System.out.println("缓冲没有数据,查询数据库");
			//2:准备分类数据
			List<CategoryBean> categoryList = productService.findAllCategory();
			//3:Gson创建Gson对象
			Gson gson = new Gson();
			//4:把对象转换成json字符串
			categoryListJson = gson.toJson(categoryList);
			jedis.set("categoryListJson", categoryListJson);
		}
		
		//5:响应数据，如果使用application/json;charset=UTF-8,jquery自动转换为json对象
		response.setContentType("application/json;charset=UTF-8");
		//6:响应回客户端
		response.getWriter().write(categoryListJson);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
