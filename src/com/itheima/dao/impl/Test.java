package com.itheima.dao.impl;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author:XueYi
 * @time:2018年4月1日 下午10:55:19
 * @version:1.0
 * @company:songbai
 */
public class Test {

	public static void main(String[] args) {
		System.out.println(StringUtils.isBlank("sss"));
		System.out.println(StringUtils.isBlank(""));
		System.out.println(StringUtils.isBlank(null));
		System.out.println(StringUtils.isBlank("  "));
	}
}
