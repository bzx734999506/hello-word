package com.bankledger.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import net.sf.json.JSONObject;

public class bzx {

	public static void main(String[] args) {
		//ip存配置文件里
		IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");
		try {
			ipfs.refs.local();
			System.out.println("Ip连接成功！！！");
			
			//添加文件使用（add方法返回merklenodes列表，在这种情况下只有一个元素）：
			/*NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File("D:/hello123.txt"));
			MerkleNode addResult = ipfs.add(file).get(0);
			System.out.println("添加成功！！！");
			
			JSONObject aJsonObject = JSONObject.fromObject(addResult.toJSONString());
			
			System.out.println("toJSONString="+ addResult.toJSONString());
			System.out.println("toString="+ addResult.toString());
			System.out.println("hashCode="+ addResult.hashCode());
			System.out.println("hash="+ aJsonObject.getString("Hash"));
			System.out.println("Name="+ aJsonObject.getString("Name"));*/
			
			//要添加一个字节[]，请使用：
			/*NamedStreamable.ByteArrayWrapper file = new NamedStreamable.ByteArrayWrapper("D:/hello123.txt", "G'day world! IPFS rocks!".getBytes());
			System.out.println(file);
			MerkleNode addResult = ipfs.add(file).get(0);
			System.out.println("添加成功！！！");
			JSONObject aJsonObject = JSONObject.fromObject(addResult.toJSONString());
			
			System.out.println("toJSONString="+ addResult.toJSONString());
			System.out.println("toString="+ addResult.toString());
			System.out.println("hashCode="+ addResult.hashCode());
			System.out.println("hash="+ aJsonObject.getString("Hash"));
			System.out.println("Name="+ aJsonObject.getString("Name"));*/
			
			//获取文件使用：
			/*Multihash filePointer = Multihash.fromBase58("Qmc7H7uVuDWPKLQDvynMegKP7L5FpEJD83requxXucKxJn");
			byte[] fileContents = ipfs.cat(filePointer);
			System.out.println("获取成功！！！");
			
			File imageFile = new File("D:/hello456.txt");
			// 创建输出流
			FileOutputStream outStream = new FileOutputStream(imageFile);
			System.out.println("保存成功！！！");
			// 写入数据
			outStream.write(fileContents);
			// 关闭输出流
			outStream.close();*/
			
			//获取字符串使用：
			/*String str = new String(fileContents);
			System.out.println("获取字符串：" + str);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
