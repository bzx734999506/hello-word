package com.bankledger.holder;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ThemeResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

public class TheamAndLocalHolder {
	
	private static org.apache.log4j.Logger log = Logger.getLogger(TheamAndLocalHolder.class);
	/**
	 * 返回当前用户正在使用的主题
	 * @param request
	 * @return
	 */
	public static String getThemeThatCurrUserUsing(HttpServletRequest request){
		ThemeResolver themeResolver = RequestContextUtils.getThemeResolver(request);	
		String themeString=themeResolver.resolveThemeName(request);
		log.info("getThemeThatCurrUserUsing:"+themeString);
		
		return themeString;
	}
	
	public static String getLocalStringThatCurrUserUsing(HttpServletRequest request){
		LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
		String localString=localeResolver.resolveLocale(request).getLanguage();
		log.info("getLocalStringThatCurrUserUsing:"+localString);
		
		return localString;
	}

}
