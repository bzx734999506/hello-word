package com.bankledger.framework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ThemeAndLocalController {
	
	@RequestMapping({"/themeAndLocal/changeLocale","/themeAndLocal/changeTheme"})
	public ModelAndView changeLocaleOrTheme()
	{
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/index");	
		return mv;
	}
}
