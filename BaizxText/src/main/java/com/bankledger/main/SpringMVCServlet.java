package com.bankledger.main;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;



@WebServlet(urlPatterns = {"/"},
loadOnStartup = 1, name = "MyFrameworkServlet", displayName = "MyFrameworkServlet", 
initParams = {@WebInitParam(name = "contextConfigLocation", value = "classpath:spring/applicationContext.xml")} 
) 
public class SpringMVCServlet extends DispatcherServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5810832056143868188L;
	
	private static org.apache.log4j.Logger log = Logger.getLogger(SpringMVCServlet.class);
	
	/**
	 * 覆盖父类初始化方法
	 */
	@Override
	protected WebApplicationContext initWebApplicationContext() {
		WebApplicationContext context = super.initWebApplicationContext();
		printCopyright();
		return context;
	}
	
	/**
	 * 打印版权信息
	 */
	private static void printCopyright(){
		log.info("**************************************************************");
		log.info("*                 深圳银链科技有限公司                                                *");
		log.info("*                 Java 后台开发平台                                                    *");
		log.info("**************************************************************");
	}

}
