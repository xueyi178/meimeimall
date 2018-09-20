package com.itheima.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.jni.User;

import com.google.gson.Gson;
import com.itheima.domain.CartBean;
import com.itheima.domain.CartItemBean;
import com.itheima.domain.CategoryBean;
import com.itheima.domain.OrderitemBean;
import com.itheima.domain.OrdersBean;
import com.itheima.domain.PageBean;
import com.itheima.domain.ProductBean;
import com.itheima.domain.UserBean;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.CommonsUtils;
import com.itheima.utils.JedisPoolUtils;
import com.itheima.utils.PaymentUtil;

import redis.clients.jedis.Jedis;
/**
 * 1:商品模块的servlet
 * @author:XueYi
 * @time:2017年7月26日 下午3:10:09
 * @version:1.0
 * @company:songbai
 */
public class PrdocutServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public PrdocutServlet(){
		System.out.println("前台的页面的servlet");
	}

	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1:获取请求的那个方法的参数的method
		String methodName = request.getParameter("method");
		if("productList".equals(methodName)){
			productList(request,response);
		}else if("categoryList".equals(methodName)){
			categoryList(request,response);
		}else if("index".equals(methodName)){
			index(request,response);
		}else if("proInfo".equals(methodName)){
			proInfo(request,response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}*/

	/**
	 * 1:显示商品类别信息的功能
	 */
	protected void categoryList(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("进入了商品类型的查询方法");
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

	/**
	 * 2:显示首页的热门商品和最新商品的功能
	 */
	protected void index(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("进入了首页的最新商品和热门商品的方法");
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

	/**
	 * 3:显示商品详细信息的功能
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void proInfo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("进入了商品详细信息的方法");
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

	/**
	 * 4:根据商品的类别获取商品的列表
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void productList(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("进入了商品类型获取商品列表的分页的方法");
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

	/**
	 * 5:将商品添加到购物车
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void addProductToCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		ProductService productService = new ProductServiceImpl();

		HttpSession session = request.getSession();
		//1:获取要放到购物车的商品的pid
		String pid = request.getParameter("pid");
		//2:获得该商品的购买数量
		int byNumber = Integer.parseInt(request.getParameter("byNumber"));

		//3:获得productService对象

		ProductBean product = productService.findProductByPid(pid);
		//4:计算小计
		double subtotal = product.getShop_price()*byNumber;
		//5:封装成一个购物车项
		CartItemBean cartItemBean = new CartItemBean();
		cartItemBean.setProductBean(product);
		cartItemBean.setByNumber(byNumber);
		cartItemBean.setSubtotal(subtotal);

		//6:获得购物车,---判断是否在session中存在购物车

		CartBean cartBean = (CartBean) session.getAttribute("cartBean");
		//7:如果没有购物车
		if(cartBean==null){
			//8:就创建一个购物车
			cartBean = new CartBean();
		}

		//判断购物车中是否包含了此购物项了---判断key是否已经存在了
		//如果购物车中已经存在此商品了--将现在买的数量与原有的数量进行相加操作
		//购物车中的购物项
		Map<String, CartItemBean> cartItemBeans = cartBean.getCartItemBeans();

		double newsubtotal = 0.0;
		if(cartItemBeans.containsKey(pid)){
			//如果包含,就取出原有的商品数量
			CartItemBean cartItemBean2 = cartItemBeans.get(pid);
			//修改的是数量
			Integer oldBuyNumber = cartItemBean2.getByNumber();
			oldBuyNumber+=byNumber;
			cartItemBean2.setByNumber(oldBuyNumber);
			cartBean.setCartItemBeans(cartItemBeans);
			//修改的是小计
			//原来商品的小计
			double oldsubtotal = cartItemBean2.getSubtotal();
			//新买的商品的小计
			newsubtotal = byNumber*product.getShop_price();
			cartItemBean2.setSubtotal(newsubtotal+oldsubtotal);
		}else{
			//9:如果没有包含,就把购物车项放到购物车中 ---kiy是pid
			cartBean.getCartItemBeans().put(product.getPid(), cartItemBean);
			newsubtotal = byNumber*product.getShop_price();
		}

		//8.1:计算总计
		double total = cartBean.getTotal()+newsubtotal ;
		cartBean.setTotal(total);

		//10:将购物车放到session中
		session.setAttribute("cartBean", cartBean);

		//11:直接跳转到购物车页面
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	/**
	 * 6:删除单一商品的方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void delProFromCart(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1:获取要删除的pid
		String pid = request.getParameter("pid");
		//2:删除session中的购物车中的购物项集合中的cartItemBeans项
		HttpSession session = request.getSession();
		CartBean cartBean = (CartBean) session.getAttribute("cartBean");
		if(cartBean!=null){
			Map<String, CartItemBean> itemBeans = cartBean.getCartItemBeans();

			//如果真的删除了,需要修改总价
			cartBean.setTotal(cartBean.getTotal()-itemBeans.get(pid).getSubtotal());
			//在进行删除
			itemBeans.remove(pid);
			cartBean.setCartItemBeans(itemBeans);

		}
		//3:把购物车放到session中
		session.setAttribute("cartBean", cartBean);
		//4:跳回购物车页面
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	/**
	 * 7:清空购物车的方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void clearCrat(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1:获取session对象
		HttpSession session = request.getSession();
		//2:删除购物车
		session.removeAttribute("cartBean");
		//3:重定向购物车页面
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
	}

	/**
	 * 8:提交订单的方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void submitOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1:获得session对象
		HttpSession session = request.getSession();
		//2:判断用户是否已经登录,如果没有登录下面代码不执行
		UserBean userBean = (UserBean) session.getAttribute("userBean");
		if(userBean==null){
			//3:没有登录,就跳到登录的页面
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}

		//4:封装好一个OrdersBean转递给Service层
		OrdersBean ordersBean = new OrdersBean();
		//4.1:订单的id
		String oid = CommonsUtils.getUUID();
		ordersBean.setOid(oid);
		//4.2:下单的时间
		ordersBean.setOrdertime(new Date());
		//4.3:订单的总金额
		//4.4:获得购物车
		CartBean cartBean = (CartBean) session.getAttribute("cartBean");
		double total = 0.0;
		if(cartBean!=null){
			total = cartBean.getTotal();
		}
		ordersBean.setTotal(total);
		//4.5:支付的状态,1:表示已支付,0:表示未支付
		ordersBean.setState(0);
		//4.6:收货的地址
		ordersBean.setAddress(null);
		//4.7:收货人的联系方式
		ordersBean.setTelephone(null);
		//4.8:收货人的姓名
		ordersBean.setName(null);
		//4.9:该订单属于哪个用户
		ordersBean.setUserBean(userBean);
		//4.10:该订单有多少个订单项
		//4.11:获得购物车中购物项的集合Map
		Map<String, CartItemBean> cartItemBeans = cartBean.getCartItemBeans();
		for(Map.Entry<String, CartItemBean> iterable_element : cartItemBeans.entrySet()) {
			CartItemBean cartItemBean = iterable_element.getValue();
			//4.12:订单项的实体
			OrderitemBean orderitemBean = new OrderitemBean();
			//4.13:订单项的id
			orderitemBean.setItemid(CommonsUtils.getUUID());
			//4.14:订单项内的商品实体的数量
			orderitemBean.setCount(cartItemBean.getByNumber());
			//4.15:订单项的小计
			orderitemBean.setSubtotal(cartItemBean.getSubtotal());
			//4.16:订单项内部的商品
			orderitemBean.setProductBean(cartItemBean.getProductBean());
			//4.17:该订单项属于哪个订单
			orderitemBean.setOrdersBean(ordersBean);
			//5:将该订单项添加到订单的集合中
			ordersBean.getOrderitemBeans().add(orderitemBean);
		}

		//6:调用业务逻辑,转递数据到servlice层
		ProductService productService = new ProductServiceImpl();
		productService.submitOrder(ordersBean);

		//7:页面跳转
		session.setAttribute("ordersBean", ordersBean);
		//8:重定向我的页面
		response.sendRedirect(request.getContextPath()+"/order_info.jsp");
	}

	/**
	 * 9:确认订单---更新收货人的地址+在线支付
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void confirmOrder(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1:更新收货人数据
		Map<String, String[]> parameterMap = request.getParameterMap();
		OrdersBean ordersBean = new OrdersBean();
		try {
			BeanUtils.populate(ordersBean, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		ProductService productService = new ProductServiceImpl();
		productService.updateOrderAdrr(ordersBean);
		//2:在线支付的方法
		//2.1:获得选择的银行
		//订单的id
		String oid = request.getParameter("oid");
		//支付的金额
		//String money = ordersBean.getTotal()+"";
		String money = "0.01";
		//银行
		String pd_FrpId = request.getParameter("pd_FrpId");
		//2.2:只接入一个接口,这个接口集成了所有的银行接口,这个接口是第三方支付平台的提供
		//接入的是易宝支付
		// 发给支付公司需要哪些数据
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = oid;
		String p3_Amt = money;
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);


		String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
				"&p0_Cmd="+p0_Cmd+
				"&p1_MerId="+p1_MerId+
				"&p2_Order="+p2_Order+
				"&p3_Amt="+p3_Amt+
				"&p4_Cur="+p4_Cur+
				"&p5_Pid="+p5_Pid+
				"&p6_Pcat="+p6_Pcat+
				"&p7_Pdesc="+p7_Pdesc+
				"&p8_Url="+p8_Url+
				"&p9_SAF="+p9_SAF+
				"&pa_MP="+pa_MP+
				"&pr_NeedResponse="+pr_NeedResponse+
				"&hmac="+hmac;

		//重定向到第三方支付平台
		response.sendRedirect(url);
	}
	
	/**
	 * 10:获得订单详细信息的方法
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void myOrders(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1:获取session对象
		HttpSession session = request.getSession();
		//2:在session里面获取用户对象
		UserBean userBean = (UserBean) session.getAttribute("userBean");
		//3:判断用户是否登录
		if(userBean == null){
			//4:没有登录,通知用户去登录
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}
		//5:业务逻辑接口指向业务逻辑层实现类
		ProductService productService = new ProductServiceImpl();
		//6:查询该用户所有的订单信息(单表查询所有的orders表)
		//集合中的每一个Orders对象的数据是不完整的,缺少private List<OrderitemBean> orderitemBeans ;
		List<OrdersBean> ordersBeansList = productService.findAllOrder(userBean.getUid());
	
		//7:选好所有的订单,为每个订单填充订单项的集合的信息
		if(ordersBeansList!=null){
			for (OrdersBean ordersBean : ordersBeansList) {
				//8:获得每一个订单的oid
				String oid = ordersBean.getOid();
				//9:查询该订单的所有的订单项,mapList封装的是多个订单项和该订单项中商品的信息
				List<Map<String, Object>>  orderitemBeansList = productService.findAllOrderItemByOid(oid);
				//10:将mapList转换成private List<OrderitemBean> orderitemBeans;
				for (Map<String, Object> map : orderitemBeansList) {
					try {
						//11:从你的map中取出count、subtotal封装到OrderitemBean中
						OrderitemBean orderitemBean = new OrderitemBean();
						BeanUtils.populate(orderitemBean, map);
						
						//12:从map中取出pname、pimage、shop_price封装到ProductBean中
						ProductBean productBean = new ProductBean();
						BeanUtils.populate(productBean, map);
						
						//13:将ProductBean封装到OrderitemBean中
						orderitemBean.setProductBean(productBean);
						
						//14:将OrderitemBean封装到ordersBean中的orderitemBeans中
						ordersBean.getOrderitemBeans().add(orderitemBean);
						
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		//15:ordersBeansList订单集合封装完成
		request.setAttribute("ordersBeansList",ordersBeansList);
		//16:进行转发
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}
}