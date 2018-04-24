package com.bankledger.framework.Interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bankledger.framework.bean.Account;
import com.bankledger.framework.dao.DBMenuDao;

public class URLPermissionInterceptor extends HandlerInterceptorAdapter {
	
	private List<String> noNeedAuthUrlList ;
	
	@Autowired
	DBMenuDao   menuDao;
	
	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 
		
        String requestUri = request.getRequestURI();  
        String contextPath = request.getContextPath();  
        String url = requestUri.substring(contextPath.length());  
        System.out.println("URL:"+url);   
        Account account =null;
        
        List<String> allowUrlList;
		HttpSession session = request.getSession(false);
		if(null==session){
			allowUrlList =getNoNeedAuthUrlList();
		}
		else
		{
			account = (Account) session.getAttribute("ACCOUNT");
			if(null==account){
				allowUrlList =getNoNeedAuthUrlList();
			}
			else
			{
				if("0".equals(account.getPermission().getGroup()))
				{
					return true;
				}
				allowUrlList = account.getPermission().getAllowMenuUrl();
			}
		}
		
		boolean rs= isAllowURL(allowUrlList,url);
		
		if(!rs){
			if(null==account){//未登陆用户，返回登陆页面，重新登陆
			    java.io.PrintWriter out = response.getWriter();  
			    out.println("<html>");  
			    out.println("<script>");  
			    String loginUrl=request.getContextPath()+"/index";
			    String scriptOPen="window.open ('"+loginUrl+"','_top')";
			    out.println(scriptOPen); 
			    out.println("</script>");  
			    out.println("</html>");  
			   
			}else{//已登录用户，返回未授权
				request.getRequestDispatcher("/WEB-INF/views/system/login/404.jsp").forward(request, response); 
			}
			
		}
		
		return rs;
	}
	
	public boolean isAllowURL(List<String> allowUrlList,String url){
		
		for(String urltmp:allowUrlList){
			if(url.equals(urltmp)){
				return true;
			}
		}
		return false;
	}
	
	
	
	public List<String> getNoNeedAuthUrlList(){
		if(null==noNeedAuthUrlList){
			noNeedAuthUrlList = menuDao.getNoNeedAuthMenuURL();
		}
		return noNeedAuthUrlList;
	}
	

}
