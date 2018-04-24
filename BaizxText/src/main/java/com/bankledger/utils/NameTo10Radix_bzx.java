package com.bankledger.utils;

public class NameTo10Radix_bzx {

	public static final String Radix10(String name) {
		
		//String a = "哲也";
		char[] c = name.toCharArray();
		String toName = "";
		String toName1 = "";
		for (int i = 0; i < c.length; i++) {
			
			char aString = c[i];
			
			//获得字符的uncoide编码
			String uncoide = Integer.toHexString(aString);
			System.out.println(c[i]+"的uncoide编码:\t"+uncoide);
			
			//从uncoide编码转换成10进制
			int x = Integer.parseInt(uncoide, 16);
			System.out.println(uncoide+"转成10进制:\t"+x);
			
			toName += String.valueOf(x) + "_";
			
			//从10进制转成uncoide编码
			String y = Integer.toHexString(x);
			System.out.println(x +"转成uncoide编码:\t"+ y);
			
			char b = (char) Integer.parseInt(y, 16);
			System.out.println(y +"的char编码:\t"+ b);
			
			String dString = Character.toString(b);
			System.out.println(b +"的utf-8编码:\t"+ dString);
			
			toName1 += dString + "_";
		}
		
		toName1 = toName1.substring(0,toName1.length()-1);
		
		System.out.println("名字为:\t" + toName1);
		
		toName = toName.substring(0,toName.length()-1);
				
		System.out.println("名字为:\t" + toName);
		
		return toName;
	}

}
