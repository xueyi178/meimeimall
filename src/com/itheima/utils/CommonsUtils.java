package com.itheima.utils;

import java.util.UUID;

/**
 * 1:生产UUID的类
 * @author:XueYi
 * @time:2017年7月21日 上午10:29:08
 * @version:1.0
 * @company:songbai
 */
public class CommonsUtils {

	//1:生产UUID的方法
	public static String getUUID(){
		return UUID.randomUUID().toString();
	}
}
