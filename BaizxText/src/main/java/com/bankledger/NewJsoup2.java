package com.bankledger;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewJsoup2 {

	public static void main(String[] args) throws IOException {
		
		String Url = "http://www.dytt8.net/html/gndy/jddy/20180418/56724.html";
		Url = "http://www.anwang.com/";
		//得到有用的信息
		Document doc = Jsoup.connect(Url).get();
		
		//System.out.println(doc);
		if(doc.text().contains("research")){
			System.out.println(Url);
		}

		//得到所有的链接，并递归调用
		Elements questions = doc.select("img[src]");
		System.out.println("questions : " + questions);
		
		/*for(Element link: questions){
			if((!link.attr("href").contains("www"))&&(!"".equals(link.text()))&&("20".equals(link.text().substring(0,2)))){
				System.out.println("title : " + link.text());
				String href =  Url.substring(0,Url.length()-1)+link.attr("href");
				System.out.println("href : " + href);
			}
		}*/
		/*for(Element link: questions){
			if((link.attr("fkyymkch").contains("thunder:"))){
				System.out.println("title : " + link.text());
				String href =  Url.substring(0,Url.length()-1)+link.attr("href");
				System.out.println("href : " + href);
				System.out.println(link.attr("fkyymkch"));
			}
		}*/

	}

}
