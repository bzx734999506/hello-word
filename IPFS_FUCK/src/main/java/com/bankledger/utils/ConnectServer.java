package com.bankledger.utils;

import java.util.Properties;

import io.ipfs.api.IPFS;

/**
 * @author bzx
 *
 */
public class ConnectServer {
	
	/** 连接ipfs服务器
	 * @return 
	 * @return IPFS
	 */
	public static IPFS Connect() {
		Properties ppt = new Properties();
		IPFS ipfs = null;
		try {
			
			ppt.load(ConnectServer.class.getClassLoader().getResourceAsStream("properties/config.properties"));
			
			String ipfsUrl = (String) ppt.get("ipfsUrl");
			System.out.println(ipfsUrl);
			
			ipfs = new IPFS(ipfsUrl);
			
			ipfs.refs.local();
			System.out.println("Ip连接成功！！！");
			
		}catch (Exception e) {
			
			System.out.println("Ip连接失败！！！");
			e.printStackTrace();
			
		}
		
		return ipfs;
	}
}
