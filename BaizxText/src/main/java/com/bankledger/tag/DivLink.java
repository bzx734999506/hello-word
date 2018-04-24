package com.bankledger.tag;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.bankledger.framework.bean.Account;


public class DivLink extends TagSupport {


	private static final long serialVersionUID = 8150237386260219530L;

	private String url;	
	
	private String name;
	
	private String style;
	
	private String cssClass;
	
	private String id;
	
	private String onclick;
	
	private String dataOptions;

	private static final Pattern urlPattern = Pattern.compile("\\Q{url}\\E");
	
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
			if(null!=onclick){
				Matcher matcher = urlPattern.matcher(onclick);
				if(matcher.find()){
					onclick = matcher.replaceAll(url);
				}
			}
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
		System.out.println("name:"+name);
		System.out.println("style:"+style);
		System.out.println("cssClass"+cssClass);
		System.out.println("id:"+id);
		System.out.println("onclick:"+onclick);
		System.out.println("dataOptions:"+dataOptions);
	}
	
	public String getATagString(){
		StringBuffer divLink=new StringBuffer("<div ");
		addAttribute(divLink,"url",url);	
		addAttribute(divLink,"style",style);
		addAttribute(divLink,"class",cssClass);
		addAttribute(divLink,"id",id);
		addAttribute(divLink,"onclick",onclick);
		addAttribute(divLink,"data-options",dataOptions);
		divLink.append(">"+name+"</div>");		
		return divLink.toString();
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getDataOptions() {
		return dataOptions;
	}

	public void setDataOptions(String dataOptions) {
		this.dataOptions = dataOptions;
	}

	
	
	
}
