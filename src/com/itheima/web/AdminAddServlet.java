package com.itheima.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.itheima.domain.CategoryBean;
import com.itheima.domain.ProductBean;
import com.itheima.service.AdminService;
import com.itheima.service.impl.AdminServiceImpl;
import com.itheima.utils.CommonsUtils;
/**
 * 1:后台商品添加的方法
 * @author:XueYi
 * @time:2017年7月31日 上午11:13:03
 * @version:1.0
 * @company:songbai
 */
@WebServlet("/adminAddServlet")
public class AdminAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		try {

			//1:获取表单的数据封装一个Product的实体,将图片上传给服务器
			//2:创建一个DiskFileItemFactory(磁盘文件项)工厂对象
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

			//3:使用工厂解析器来创建ServletFileUpload核心对象
			ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);


			//解决上传文件的乱码
			fileUpload.setHeaderEncoding("UTF-8");
			//3:使用解析器来解析request对象
			List<FileItem> fileItems = new ArrayList<FileItem>(0); 
			//用于封装普通表单项的数据
			Map<String, Object> map = new HashMap<String, Object>();


			//4:使用fileUpload解析器来解析request对象
			List<FileItem> parseRequest = fileUpload.parseRequest(request);
			//5:遍历每个表单项的数据
			for (FileItem fileItem : parseRequest) {
				//6:判断当前表单字段是否为普通文本字段
				if(fileItem.isFormField()){
					//7:普通表单项,获得表单的数据,封装到Product的实体中
					//7.1:获得普通项的文件名
					String fieldName = fileItem.getName();
					//7.2:获得普通项的value
					String fieldValue = fileItem.getString("utf-8");
					map.put(fieldName, new String[]{fieldValue});
				}else{
					//8:文件上传表单项
					//8.1:上传项的文件名
					String fileName = fileItem.getName();
					//8.2:得到上传文件的输入流
					InputStream inputStream = fileItem.getInputStream();
					//8.2:得到文件上传的扩展名
					String extensionName = FilenameUtils.getExtension(fileName);
					//8.3:上传文件不能是jsp和exe
					if("jsp".equals(extensionName) || "exe".equals(extensionName)){
						//8.4:创建文件存盘的目录
						File storeDirectory = new File(this.getServletContext().getRealPath("/upload"));
						//8.5:判断存盘的目录是否存在
						if(!storeDirectory.exists()){
							//8.6:如果目录不存在，就创建
							storeDirectory.mkdirs();
						}
						//8.7:处理文件名
						if(fileName!=null){
							fileName = FilenameUtils.getName(fileName);
						}
						//8.8:目录打散
						String childDirectory = makeChilDirectory(storeDirectory,fileName);
						//8.9:文件打散
						fileName = childDirectory+File.separator+fileName;
						//8.10:文件上传
						fileItem.write(new File(storeDirectory,fileName));
						//8.11:删除临时文件
						fileItem.delete();
					}
					
					//8.12:将图片表单项的name和value保存到map中
					map.put(fileItem.getFieldName(), new String[]{fileName});
				}
			}
			
			//9:封装到实体类
			ProductBean productBean = new ProductBean();
			//用来将一些 key-value 的值（例如 hashmap）映射到 bean 中的属性
			BeanUtils.populate(productBean, map);
			//1.3:添加数据时,编号自动提交
			productBean.setPid(CommonsUtils.getUUID());
			productBean.setPdate(new Date());
			productBean.setPflag(0);
			//创建一个
			CategoryBean categoryBean = new CategoryBean();
			categoryBean.setCid(map.get("cid").toString());
			productBean.setCategory(categoryBean);
			//2:调用业务逻辑
			AdminService adminService = new AdminServiceImpl();
			adminService.addProduct(productBean);
			//3:分发装向
			//不写/代表相对路径，相对于本类的路径
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private String makeChilDirectory(File storeDirectory, String fileName) {
		//返回字符串转换的32位hashCode码
		int hashCode = fileName.hashCode();
		System.out.println(hashCode);
		//把hashCode转换为16进制的字符,在返回字符串
		String hexString = Integer.toHexString(hashCode);
		System.out.println(hexString);
		String clildDirectory = hexString.charAt(0)+File.separator+hexString.charAt(1);
		//创建指定目录
		File file = new File(storeDirectory,clildDirectory);
		if(!file.exists()){
			file.mkdirs();
		}
		return clildDirectory;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}

}
