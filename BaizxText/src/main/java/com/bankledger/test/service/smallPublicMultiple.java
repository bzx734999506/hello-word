package com.bankledger.test.service;

import java.util.Scanner;

public class smallPublicMultiple {

	public static void main(String[] args) {
	
		Scanner in = new Scanner(System.in);
		int a = in.nextInt();
		int b = in.nextInt();
		int c = a * b;
		if(a < b){
			int r = 0;
			r = a;
			a = b;
			b = r;
		}
		while(true){
			int r = a % b;
			if(r == 0){
				System.out.println("最小公倍数："+c / b);
				break;
			}else{
				a = b;
				b = r;
			}
		}
	}

}
