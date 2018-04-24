package com.bankledger.test.service;

public class enumTest {
	public static void main(String args[]) {
		FreshJuice juice = new FreshJuice();
		juice.size = FreshJuice.FreshJuiceSize.SMALL;
		System.out.println(juice.size);
	}
}

class FreshJuice {
	enum FreshJuiceSize {
		SMALL, MEDUIM, LARGE
	}

	FreshJuiceSize size;
}
