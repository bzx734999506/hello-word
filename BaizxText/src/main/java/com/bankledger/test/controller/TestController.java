package com.bankledger.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bankledger.test.bean.Text;
import com.bankledger.test.service.TextService;

@Controller
public class TestController {
	
	@Autowired
	TextService textService;
	
	@RequestMapping("/testIndex")
	public String testIndex(){
		return "Test/testIndex";
	}
	
	@RequestMapping("/queryIndex")
	@ResponseBody
	public List<Text> queryIndex(){
		return textService.queryIndex();
	}
}
