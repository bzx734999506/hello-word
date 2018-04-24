package com.bankledger.test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ceshi {

	    private static Integer[] zp = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,  
	            18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,  
	            39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54};  
	    private static Map<Integer,String> mapN = new HashMap<>();  
	      
	    public static void initMap (){  
	        mapN.put(1, "A%");      mapN.put(2, "A&");      mapN.put(3, "A#");      mapN.put(4, "A$");  
	        mapN.put(5, "2%");      mapN.put(6, "2&");      mapN.put(7, "2#");      mapN.put(8, "2$");  
	        mapN.put(9, "3%");      mapN.put(10, "3&");     mapN.put(11, "3#");     mapN.put(12, "3$");  
	        mapN.put(13, "4%");     mapN.put(14, "4&");     mapN.put(15, "4#");     mapN.put(16, "4$");  
	        mapN.put(17, "5%");     mapN.put(18, "5&");     mapN.put(19, "5#");     mapN.put(20, "5$");  
	        mapN.put(21, "6%");     mapN.put(22, "6&");     mapN.put(23, "6#");     mapN.put(24, "6$");  
	        mapN.put(25, "7%");     mapN.put(26, "7&");     mapN.put(27, "7#");     mapN.put(28, "7$");  
	        mapN.put(29, "8%");     mapN.put(30, "8&");     mapN.put(31, "8#");     mapN.put(32, "8$");  
	        mapN.put(33, "9%");     mapN.put(34, "9&");     mapN.put(35, "9#");     mapN.put(36, "9$");  
	        mapN.put(37, "10%");    mapN.put(38, "10&");    mapN.put(39, "10#");    mapN.put(40, "10$");  
	        mapN.put(41, "J%");     mapN.put(42, "J&");     mapN.put(43, "J#");     mapN.put(44, "J$");  
	        mapN.put(45, "Q%");     mapN.put(46, "Q&");     mapN.put(47, "Q#");     mapN.put(48, "Q$");  
	        mapN.put(49, "K%");     mapN.put(50, "K&");     mapN.put(51, "K#");     mapN.put(52, "K$");  
	        mapN.put(53, "w1");     mapN.put(54, "w2");  
	    }  
	      
	    public static void main(String[] args) {  
	        initMap ();  
	        List<Integer> list = Arrays.asList(zp);  
	        Collections.shuffle(list);  
	        List<Integer> play1 = list.subList(0, 17);  
	        List<Integer> play2 = list.subList(17, 34);  
	        List<Integer> play3 = list.subList(34, 51);  
	        List<Integer> dzp = list.subList(51, 54);  
	          
	        Collections.sort(play1);  
	        Collections.sort(play2);  
	        Collections.sort(play3);  
	        Collections.sort(dzp);  
	        for (Integer en:play1){  
	            System.out.print(mapN.get(en)+" ");  
	        }  
	        System.out.println();  
	        for (Integer en:play2){  
	            System.out.print(mapN.get(en)+" ");  
	        }  
	        System.out.println();  
	        for (Integer en:play3){  
	            System.out.print(mapN.get(en)+" ");  
	        }  
	        System.out.println();  
	        for (Integer en:dzp){  
	            System.out.print(mapN.get(en)+" ");  
	        }  
	          
	    }  
}
