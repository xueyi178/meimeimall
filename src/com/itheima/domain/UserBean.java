package com.itheima.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 1:用户的实体类
 * @author:XueYi
 * @time:2017年7月21日 上午9:15:34
 * @version:1.0
 * @company:songbai
 */
public class UserBean implements Serializable{
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 3825005752171515397L;
	  
	  private String uid;
	  private String username;
	  private String password;
	  private String name;
	  private String email;
	  private String telephone;
	  private Date birthday;
	  private String sex;
	  private Integer state;
	  private String code;
	  
	  /**
	   * 
	   * @param uid
	   * 		用户id
	   * @param username
	   * 		用户账号
	   * @param password
	   * 		用户密码
	   * @param name
	   * 		用户姓名
	   * @param email
	   * 		用户邮箱地址
	   * @param telephone
	   * 		用户联系方式
	   * @param birthday
	   * 		用户的出生日期
	   * @param sex
	   * 		用户的性别
	   * @param state
	   * 		用户的状态
	   * @param code
	   * 		用户的激活码
	   */
	public UserBean(String uid, String username, String password, String name, String email, String telephone,
			Date birthday, String sex, Integer state, String code) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.telephone = telephone;
		this.birthday = birthday;
		this.sex = sex;
		this.state = state;
		this.code = code;
	}
	
	public UserBean() {
		super();
	}

	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return "UserBean [uid=" + uid + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", email=" + email + ", telephone=" + telephone + ", birthday=" + birthday + ", sex=" + sex
				+ ", state=" + state + ", code=" + code + "]";
	}
}
