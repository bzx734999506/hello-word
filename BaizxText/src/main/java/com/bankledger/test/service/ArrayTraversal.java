package com.bankledger.test.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ArrayTraversal {
	
	
	    public static void main(String[] args)
	    {
	        int[] arr = {1, 2, 3, 4, 5};
	        
	        System.out.println("----------旧方式遍历------------");
	        //旧式方式        
	        for(int i=0; i<arr.length; i++)
	        {
	            System.out.println(arr[i]);
	        }
	        
	        System.out.println("---------新方式遍历-------------");
	        
	        //新式写法,增强的for循环 
	        for(int element:arr)
	        {
	            System.out.println(element);
	        }
	        
	        System.out.println("---------遍历二维数组-------------");
	        
	        //遍历二维数组 
	        int[][] arr2 = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}} ;
	        
	        for(int[] row : arr2)
	        {
	            for(int element : row)
	            {
	                System.out.println(element);
	            }
	        }
	        
	        //以三种方式遍历集合List         
	        List<String> list = new ArrayList<String>();
	        
	        list.add("a");
	        list.add("b");
	        list.add("c");
	        
	        System.out.println("----------方式1-----------");
	        //第一种方式，普通for循环 
	        for(int i = 0; i < list.size(); i++)
	        {
	            System.out.println(list.get(i));
	            
	        }
	        
	        System.out.println("----------方式2-----------");
	        //第二种方式，使用迭代器 
	        for(Iterator<String> iter = list.iterator(); iter.hasNext();)
	        {
	            System.out.println(iter.next());
	        }
	        System.out.println("----------方式3-----------");
	        //第三种方式，使用增强型的for循环 
	        for(String str: list)
	        {
	            System.out.println(str);
	            
	        }
	        
	        
	        Map<String, Integer> items = new HashMap<>();
	        items.put("A", 10);
	        items.put("B", 20);
	        items.put("C", 30);
	        items.put("D", 40);
	        items.put("E", 50);
	        items.put("F", 60);

	        items.forEach((k,v)->System.out.println("Item : " + k + " Count : " + v));

	        items.forEach((k,v)->{
	            System.out.println("Item : " + k + " Count : " + v);
	            if("E".equals(k)){
	                System.out.println("Hello E");
	            }
	        });
	    }

	
}
