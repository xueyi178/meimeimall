package com.itheima;
/**
 * 
 * @author:XueYi
 * @time:2018年5月4日 上午10:18:28
 * @version:1.0
 * @company:songbai
 */
public class Test {
	public static void main(String[] args) {
		int[] ss = {1,4,6,2,8,7,9,45,22};
		for(int i=0; i<=ss.length-1;i++){
			for(int j =0; j<=ss.length-1-i;j++){
				if(ss[j]<ss[j+1]){
					int temp = ss[j];
					ss[j] = ss[j+1];
					ss[j+1] = temp;
				}
			}
			System.out.println(ss[i]);
		}
	}

}
