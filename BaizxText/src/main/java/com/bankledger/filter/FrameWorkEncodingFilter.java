package com.bankledger.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import org.springframework.web.filter.CharacterEncodingFilter;

@WebFilter(filterName="encodingFilter",urlPatterns="/*",
initParams = {@WebInitParam(name = "encoding", value = "utf-8"),@WebInitParam(name = "forceEncoding", value = "true")})
public class FrameWorkEncodingFilter extends CharacterEncodingFilter {

}
