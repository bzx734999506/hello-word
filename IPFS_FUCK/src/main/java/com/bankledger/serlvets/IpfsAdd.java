package com.bankledger.serlvets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankledger.utils.ConnectServer;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import net.sf.json.JSONObject;

@WebServlet(name="IpfsAdd",urlPatterns="/IpfsAddServlet")
public class IpfsAdd extends HttpServlet{

	private static final long serialVersionUID = -4776417181721718468L;

	private static String CHARSET = "UTF-8";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding(CHARSET);
		resp.setCharacterEncoding(CHARSET);
		
		//连接ipfs服务器
		IPFS ipfs = ConnectServer.Connect();
		
		//获取参数
		String filePath=req.getParameter("filePath");
		NamedStreamable.FileWrapper file = new NamedStreamable.FileWrapper(new File(filePath));
		MerkleNode addResult = ipfs.add(file).get(0);
		
		System.out.println("添加成功！！！");
		System.out.println("toJSONString="+ addResult.toJSONString());
		
		JSONObject aJsonObject = JSONObject.fromObject(addResult.toJSONString());
		
		System.out.println("Hash="+ aJsonObject.getString("Hash"));
		System.out.println("Name="+ aJsonObject.getString("Name"));
		System.out.println("Size="+ aJsonObject.getString("Size"));
		
		String result = aJsonObject.getString("Hash");
		
		resp.setHeader("Content-type", "text/html;charset=UTF-8");   
		OutputStream out = resp.getOutputStream();  
		out.write(result.getBytes("UTF-8"));
		out.flush();
		out.close();
		
	}
	
}
