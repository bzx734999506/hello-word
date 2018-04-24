package com.bankledger;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.bankledger.utils.Base64Util;
import com.bankledger.utils.HttpsUtils;

public class bzx {

	public static void main(String[] args) {
		
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> map1 = new HashMap<String, String>();
		String ResponseJson = null;
		
		try {
			
			File file = new File("D:/123.gif");
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
			byte[] b = new byte[1024];
			int n;
			// 每次从fis读1000个长度到b中，fis中读完就会返回-1
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			
			fis.close();
			bos.close();
			byte[] fileByte = bos.toByteArray();

			String base64 = Base64Util.encode(fileByte);
			
			map.put("file", base64);
			/*map.put("name1", "123.gif");*/
			
			ResponseJson = HttpsUtils.post("http://10.0.0.99/IPFS_FUCK/IpfsAdd_Base64Servlet", map);
			System.out.println("ResponseJson: " + ResponseJson);
			
			map1.put("hash", ResponseJson);
			
			byte[] fileContents = HttpsUtils.post_bytes("http://10.0.0.99/IPFS_FUCK/IpfsCatServlet", null ,map1);
			
			System.out.println("fileContents.length bzx: " + fileContents.length);
			
			FileOutputStream outStream = new FileOutputStream("D:/bzx1234.gif");
			// 写入数据
			outStream.write(fileContents);
			System.out.println("保存成功！！！"); 
			// 关闭输出流 
			outStream.flush();
			outStream.close();
			System.out.println("关闭输出流！！！");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
