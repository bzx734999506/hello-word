package com.bankledger.springBean;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class MyProperties extends PropertyPlaceholderConfigurer {
	
	private static Properties myProperties=new Properties();
	
//	private static org.apache.log4j.Logger log = Logger.getLogger(MyProperties.class);
	
	public  void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)throws BeansException{
		super.processProperties(beanFactory, props);		
		setMyProperties(props);
		
	}

	public static Properties getMyProperties() {
		return myProperties;
	}

	public static void setMyProperties(Properties myProperties) {
		MyProperties.myProperties = myProperties;
	}
	
}
