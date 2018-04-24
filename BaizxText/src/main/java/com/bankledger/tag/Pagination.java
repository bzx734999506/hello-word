package com.bankledger.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.bankledger.framework.bean.Account;

public class Pagination extends TagSupport{

	private static final long serialVersionUID = 3222487038453136233L;
	
	private String url;	
	
	private String id;
	
	private String style;
	
	private String functionName;
	
	public int doStartTag() throws JspException 
	{
		JspWriter out = null;
		try 
		{
			boolean printlnAttributeForDebugFlag = false;
			if(printlnAttributeForDebugFlag){
				printlnAttributes();
			}
			out = pageContext.getOut();
			
			String text=getATagString();
			HttpSession session = pageContext.getSession();
			Account account =(Account) session.getAttribute("ACCOUNT");
			System.out.println(url);
			boolean flag = account.getPermission().isAllowedUrl(url);
			if(flag)
			{
				out.print(text);
			}
			
		}
		catch (Exception e) 
		{
			throw new JspException(e);
		}
		return super.doStartTag();
	}
	
	public void printlnAttributes(){
		System.out.println("url:"+url);

		System.out.println("style:"+style);

		System.out.println("id:"+id);

	}
	
	public String getATagString(){
		StringBuffer tag=new StringBuffer("<div ");
		addAttribute(tag,"style",style);
		addAttribute(tag,"id",id);
		tag.append(">");
		
		tag.append("<div style=\"width:70%;text-align:left;float:left\">");
		tag.append("<a href=\"#\" class=\"easyui-linkbutton\" id=\"First\" onclick=\""+functionName+"('"+url+"',"+"'First')\""+"><<</a>");
		tag.append("<a href=\"#\" class=\"easyui-linkbutton\" id=\"Previous\" onclick=\""+functionName+"('"+url+"',"+"'Previous')\""+"><</a>");
		tag.append("<span>page</span>");
		tag.append("<input type=\"text\" name=\"pageGo\" id=\"pageGo\" size=\"1\" style=\"text-align:center;\"/>");
		tag.append("<span>of</span>");
		tag.append("<span id=\"totalPage\"></span>");
		tag.append("<a href=\"#\" class=\"easyui-linkbutton\"  onclick=\""+functionName+"('"+url+"',"+"'Go')\""+">确定</a>");
		tag.append("<a href=\"#\" class=\"easyui-linkbutton\" id=\"Next\" onclick=\""+functionName+"('"+url+"',"+"'Next')\""+">></a>");
		tag.append("<a href=\"#\" class=\"easyui-linkbutton\" id=\"Last\" onclick=\""+functionName+"('"+url+"',"+"'Last')\""+">>></a>");
		tag.append("</div>");	
		tag.append("<div style=\"width:30%;text-align:center;;float:right\">");
		tag.append("<span>Displaying </span><span id=\"start\"></span> <span> to </span> <span id=\"end\"></span><span> of </span><span  id=\"totalRecords\"></span><span> items</span>");	
		tag.append("</div>");		
		return tag.toString();
		
	}
	
	public StringBuffer addAttribute(StringBuffer sb,String attributeName,String attributeValue)
	{
		if(null!=attributeValue)
		{
			sb.append(attributeName+"=\""+attributeValue+"\" ");
		}
		
		return sb;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	
	
	
}
