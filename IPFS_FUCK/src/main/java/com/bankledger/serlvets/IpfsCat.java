package com.bankledger.serlvets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankledger.utils.ConnectServer;

import io.ipfs.api.IPFS;
import io.ipfs.multihash.Multihash;

@WebServlet(name="IpfsCat",urlPatterns="/IpfsCatServlet")
public class IpfsCat extends HttpServlet{
	
	private static final long serialVersionUID = 459740222781561330L;
	
	private static String CHARSET = "UTF-8";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding(CHARSET);
		resp.setCharacterEncoding(CHARSET);
		
		//连接ipfs服务器
		IPFS ipfs = ConnectServer.Connect();
		
		//获取参数
		String hash=req.getParameter("hash");
		System.out.println("hash="+hash);
		
		Multihash filePointer = Multihash.fromBase58(hash);
		System.out.println("filePointer="+filePointer);
		
		byte[] fileContents = ipfs.cat(filePointer);
		
		System.out.println("fileContents.length ====="+fileContents.length);
		System.out.println("获取成功！！！");
		
		OutputStream out = resp.getOutputStream();  
		out.write(fileContents);
		out.flush();
		out.close();
		
	}
	
}
