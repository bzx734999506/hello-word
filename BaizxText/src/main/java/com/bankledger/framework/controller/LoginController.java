package com.bankledger.framework.controller;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bankledger.framework.bean.Account;
import com.bankledger.framework.service.LoginServiceInterface;

@Controller
public class LoginController {
	
	@Resource(name="CommonLoginService")
	LoginServiceInterface loginService;
	
	private static org.apache.log4j.Logger log = Logger.getLogger(LoginController.class);
	/**
	 * 首页 按照新的需求应该要去掉已经登陆的session
	 * @return
	 */
	@RequestMapping({"/", "/index"})
	public String index(){
		log.info("login....");
		//return "system/login/newIndex1";
		return "system/login/index";
	}
	
	/**
	 * 系统首页
	 * @return
	 */
	@RequestMapping("/toHomePage")
	public String toHomePage(){
		return "system/home/index";
	}
	
	/**
	 * 登陆
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/login")
	public ModelAndView login(Account account,HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView();
		if(loginService.login(account)){
			HttpSession session = request.getSession(true);//创建一个新的session，重新登陆
			session.setAttribute("ACCOUNT", account);
			System.out.println("Successfully");
			mv.setViewName("redirect:toHomePage");
		}
		else{
			System.out.println("failure");			
			mv.setViewName("redirect:index");
		}
		return mv;
	}
	
}
