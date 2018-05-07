package com.sgnbs.ms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class BaseController {
	
	@Value("${spring.profiles.active}")
	protected String active;
	
	protected final String ERROR = "201";
	protected final String SUCCESS = "200";
	
	/**
	 * 表单提交返回错误时。对mv赋值处理
	 * @param code
	 * @param msg
	 * @param mv
	 */
	protected void returnModal(String code,String msg,ModelAndView mv){
		if(null==mv){
			mv = new ModelAndView();
		}
		mv.addObject("code", code);
		mv.addObject("msg", msg);
	}
	
	protected void returnError(String msg,ModelAndView mv) {
		mv.setViewName("/common/error");
		mv.addObject("error", msg);
	}
	
	protected void returnSuccess(String msg,ModelAndView mv) {
		mv.setViewName("/common/success");
		returnModal(SUCCESS,msg,mv);
	}
	
}
