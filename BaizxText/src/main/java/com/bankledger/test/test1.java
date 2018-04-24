package com.bankledger.test;

import com.bankledger.utils.NameTo10Radix_bzx;

public class test1 {

	public static void main(String args[]) {
		/*test1 a = new test1();
		String result1 = a.locking(7349995060L);
		System.out.println(result1); 
		Long result2 = a.deblocking(result1);
		System.out.println(result2); */
		
		String aString = NameTo10Radix_bzx.Radix10("、哲也");
		System.out.println(aString);
		
	}

	private Long deblocking(String result1) {
		String news = "";
		String[] arr = result1.split("");
		String b = "";
		for (int i=0; i < arr.length; i++) {
			
			Integer a = Integer.valueOf(arr[i]) ;
			if(!"".equals(b)){
				news += Integer.valueOf(b+arr[i])/4;
				b = "";
			}else{
				if(a >= 4){
					news += a/4;
				}else if(a==0){
					news += 0;
				}else{
					b = arr[i];
				}
			}
			
		}
		return Long.valueOf(news);
	}

	public String locking(Long number) {
		String news = "";
		Long a = number;
		String b = a.toString();
		String[] arr = b.split("");

		for (String string : arr) {

			Integer change = (Integer.valueOf(string)) << 2;

			news += String.valueOf(change);
		}

		return news;
	}

}
