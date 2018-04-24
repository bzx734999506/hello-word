package com.bankledger.serlvets;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bankledger.utils.Base64Util;
import com.bankledger.utils.ConnectServer;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import net.sf.json.JSONObject;

/**
 * @author bzx
 *
 */
@WebServlet(name="IpfsAdd_Base64",urlPatterns="/IpfsAdd_Base64Servlet")
public class IpfsAdd_Base64 extends HttpServlet{
	
	private static final long serialVersionUID = 4836892197598336183L;
	private static final String CHARSET = "UTF-8";
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {	
		
		req.setCharacterEncoding(CHARSET);
		resp.setCharacterEncoding(CHARSET);
		
		//连接ipfs服务器
		IPFS ipfs = ConnectServer.Connect();
		
		//获取参数
		String name = req.getParameter("name1");
		String file = req.getParameter("file");
		
		System.out.println("name="+ name);
		//System.out.println("file="+ file);
		
		byte[] base64 = Base64Util.decode(file);
		
		System.out.println("base64="+ base64);
		
		NamedStreamable.ByteArrayWrapper file1 = new NamedStreamable.ByteArrayWrapper("123.gif", base64);
		MerkleNode addResult = ipfs.add(file1).get(0);
		
		System.out.println("添加成功！！！");
		System.out.println("toJSONString="+ addResult.toJSONString());
		
		JSONObject aJsonObject = JSONObject.fromObject(addResult.toJSONString());
		
		System.out.println("Hash="+ aJsonObject.getString("Hash"));
		System.out.println("Name="+ aJsonObject.getString("Name"));
		
		String result = aJsonObject.getString("Hash");
		
		resp.setHeader("Content-type", "text/html;charset=UTF-8");   
		OutputStream out = resp.getOutputStream();  
		out.write(result.getBytes("UTF-8"));
		out.flush();
		out.close();
		
	}
}
