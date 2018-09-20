package com.itheima.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 1:商品表的实体类
 * @author:XueYi
 * @time:2017年7月24日 下午4:59:47
 * @version:1.0
 * @company:songbai
 */
public class ProductBean implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -6505909147793714625L;
	
	  private String pid;
	  private String pname;
	  private double market_price;
	  private double shop_price;
	  private String pimage;
	  private Date pdate;
	  private Integer is_hot;
	  private String pdesc;
	  private Integer pflag;
	  private CategoryBean category;
	  
	  /**
	   * 
	   * @param pid
	   * 		商品的ID
	   * @param pname
	   * 		商品的名称
	   * @param market_price
	   * 		商品的买入价格
	   * @param shop_price
	   * 		商品的卖出价格
	   * @param pimage
	   * 		商品图片的路径
	   * @param pdate
	   * 		商品的上加日期
	   * @param is_hot
	   * 		商品是否热门
	   * @param pdesc
	   * 		商品的描述
	   * @param pflag
	   * 		商品的标识
	   * @param category
	   * 		商品的类型
	   */
	 public ProductBean(String pid, String pname, double market_price, double shop_price, String pimage, Date pdate,
			Integer is_hot, String pdesc, Integer pflag, CategoryBean category) {
		super();
		this.pid = pid;
		this.pname = pname;
		this.market_price = market_price;
		this.shop_price = shop_price;
		this.pimage = pimage;
		this.pdate = pdate;
		this.is_hot = is_hot;
		this.pdesc = pdesc;
		this.pflag = pflag;
		this.category = category;
	}
	 
	public ProductBean() {
		super();
	}
	
	
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public double getMarket_price() {
		return market_price;
	}
	public void setMarket_price(double market_price) {
		this.market_price = market_price;
	}
	public double getShop_price() {
		return shop_price;
	}
	public void setShop_price(double shop_price) {
		this.shop_price = shop_price;
	}
	public String getPimage() {
		return pimage;
	}
	public void setPimage(String pimage) {
		this.pimage = pimage;
	}
	public Date getPdate() {
		return pdate;
	}
	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}
	public Integer getIs_hot() {
		return is_hot;
	}
	public void setIs_hot(Integer is_hot) {
		this.is_hot = is_hot;
	}
	public String getPdesc() {
		return pdesc;
	}
	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}
	public Integer getPflag() {
		return pflag;
	}
	public void setPflag(Integer pflag) {
		this.pflag = pflag;
	}
	public CategoryBean getCategory() {
		return category;
	}
	public void setCategory(CategoryBean category) {
		this.category = category;
	}
	
	@Override
	public String toString() {
		return "ProductBean [pid=" + pid + ", pname=" + pname + ", market_price=" + market_price + ", shop_price="
				+ shop_price + ", pimage=" + pimage + ", pdate=" + pdate + ", is_hot=" + is_hot + ", pdesc=" + pdesc
				+ ", pflag=" + pflag + ", category=" + category + "]";
	}
}
