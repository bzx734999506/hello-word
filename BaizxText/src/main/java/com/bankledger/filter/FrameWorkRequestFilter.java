package com.bankledger.filter;

import org.springframework.web.filter.DelegatingFilterProxy;

/**
 * 
 * DelegatingFilterProxy 是一个代理的proxy 它代理一些其他的filter 通过name 进行代理
 *
 */
//@WebFilter(filterName="RequestFilter",urlPatterns="/*",
//initParams = {@WebInitParam(name = "targetFilterLifecycle", value = "true")})
public class FrameWorkRequestFilter extends DelegatingFilterProxy {

}
