package com.bankledger.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class StringUtils {

	
	/**
	 * p + yyyyMMddHHmmss + 一位 字母和数字 随机数
	 * @return
	 */
	public static String getRandomStr() {
		
		StringBuffer sb =new StringBuffer("p");
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		sb.append(time);
		sb.append(getRandomNumber(1, 3));
		return sb.toString();
	}
	
	
	/**
	 * 
	 * @param len
	 * @param type 1 数字  2字母 3数字加字母
	 * @return
	 */
	public static String getRandomNumber(Integer len, Integer type) {
		
		if(len <= 0) {
			return null;
		}
		
		// 数字48-57=10   ||  字母大写 65-90=26  || 小写97-122=26	
		
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		switch (type) {
		case 1: 
			for(int i=0 ;i < len; i++) {
				int n = random.nextInt(57-48+1)  +48;
				sb.append((char)n);
			}
			break;
		case 2: 
			for(int i=0 ;i < len; i++) {
				int n = random.nextInt(51);
				if(n >=26 && n<=51) {
					n += 71;
				}else if(n>=0 && n<=25){
					n += 65;
				}
				sb.append((char)n);
			}
			break;
		case 3: 
			for(int i=0 ;i < len; i++) {
				int n = random.nextInt(61);
				if( n >=10 && n<=35) {
					n += 55;
				}else if( n >=36 && n<= 61) {
					n += 61;
				}else if(n>=0 && n<=9) {
					n += 48;
				}
				sb.append((char)n);
			}
			break;
		default:
			break;
		}
		return sb.toString();
	}
	
	
	/**
	 * 去掉-
	 * @return
	 */
	public static String getUUID32() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 没去掉-
	 * @return
	 */
	public static String getUUID36() {
		return UUID.randomUUID().toString();
	}
}
