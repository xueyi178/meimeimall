package com.itheima.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.itheima.domain.UserBean;
import com.itheima.service.UserReginService;
import com.itheima.service.impl.UserReginServiceImpl;
import com.itheima.utils.CommonsUtils;
import com.itheima.utils.MailUtils;
/**
 * 1:注册的servlet
 * @author:XueYi
 * @time:2017年7月21日 上午9:15:11
 * @version:1.0
 * @company:songbai
 */
public class ReginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		//1:设置编码格式
		request.setCharacterEncoding("utf-8");
		//2:获得表单数据
		Map<String, String[]> parameterMap = request.getParameterMap();
		UserBean userBean = new UserBean();
		try {
			//自己指定一个类型转换器(将String装换为Date);
			ConvertUtils.register(new Converter() {
				@Override
				public Object convert(Class arg0, Object value) {
					//将String转换成Date
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					Date birthday = null;
					try {
						birthday = dateFormat.parse(value.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					return birthday;
				}
			}, Date.class);
			//隐射封装
			BeanUtils.populate(userBean, parameterMap);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		
		//2.1:封装用户ID
		userBean.setUid(CommonsUtils.getUUID());
		//2.2:封装用户联系方式
		userBean.setTelephone(null);
		//2.3:封装用户的状态
		userBean.setState(0);
		//2.4:封装用户的激活码
		String activeCode = CommonsUtils.getUUID();
		userBean.setCode(activeCode);
		
		//3:调用注册业务逻辑层,将userBean传给userBeanService
		UserReginService reginService = new UserReginServiceImpl();
		boolean regints = reginService.regint(userBean);
		//3.1:判斷是否注册成功
		if(regints){
			//发送激活邮件
			String emailMsg = "恭喜你注册成功,请点击下面的连接进行账户激活"
					+ " <a href='http://localhost:8080/HeiMaShop/active?activeCode="+activeCode+"' >"
							+ "http://localhost:8080/HeiMaShop/active?activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(userBean.getEmail(), emailMsg);
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			
			
			//3.2:跳转到成功的页面
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}else{
			//3.3:跳转到失败的页面
			response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
