package com.bankledger.aspect;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;



public class ControllerAspect {
	
	private static org.apache.log4j.Logger log = Logger.getLogger(ControllerAspect.class);
	
	public Object doAspect(ProceedingJoinPoint point) throws Exception{
		Object rs =null;
		StringBuffer sb = new StringBuffer();
		try
		{
			Method method=getPointMethod(point);
		
			System.out.println(method.toString());
			
			sb.append(method.toString());		
			beforeExecute();
			rs =point.proceed();
			afterExecute();
		}
		catch(Throwable e)
		{
			throw new Exception("异常:"+sb.toString());
		}
		finally{
			
		}
		return rs;
	}
	
	public void beforeExecute(){
		log.info("before execute........");
		
	}
	
	public void afterExecute(){
		log.info("after execute........");
	}
	
	/**
	 * 获取切点目标方法
	 * @param point 切点
	 * @return 切点的目标方法
	 */
	public static Method getPointMethod(JoinPoint point){
		Signature signature = point.getSignature();
		if(signature instanceof MethodSignature){
			MethodSignature m = (MethodSignature)signature;
			return m.getMethod();
		}
		return null;
	}
}
