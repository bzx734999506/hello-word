package com.bankledger.test;

import java.util.Arrays;
import java.util.function.IntBinaryOperator;
import java.util.function.IntUnaryOperator;

public class arraysTest {

	public static void main(String[] args) {
		
		/*int[] a = new int[]{3, 4, 5, 6};
		
		int[] a2 = new int[]{3, 4, 5, 6};
		System.out.println("a数组和a2数组是否相等："
				+ Arrays.equals(a, a2));
		int[] b = Arrays.copyOf(a, 6);
		System.out.println("a数组和b数组是否相等："
				+ Arrays.equals(a, b));
		System.out.println("b数组的元素为："
				+ Arrays.toString(b));
		Arrays.fill(b, 2, 4, 1);
		System.out.println("b数组的元素为："
				+ Arrays.toString(b));
		Arrays.sort(b);
		System.out.println("b数组的元素为："
				+ Arrays.toString(b));
		*/
		int[] arr1 = new int[]{3, -4, 25, 16, 30, 18};
		Arrays.parallelSort(arr1);
		System.out.println(Arrays.toString(arr1));
		int[] arr2 = new int[]{3, -4, 25, 16, 30, 18};
		Arrays.parallelPrefix(arr1, new IntBinaryOperator() {
			
			public int applyAsInt(int left, int right) {

				return left * right;
			}
		});
		System.out.println(Arrays.toString(arr1));
		int[] arr3 = new int[20];
		Arrays.parallelSetAll(arr3, new IntUnaryOperator() {
			
			@Override
			public int applyAsInt(int operand) {
				return operand * 5;
			}
		});
		System.out.println(Arrays.toString(arr3));
	}

}
