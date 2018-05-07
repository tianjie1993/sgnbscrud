package com.sgnbs.common.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sgnbs.common.constants.AjaxResult;


@ControllerAdvice  
public class ExcepetionHandler {  
	
	@Value("${spring.profiles.active}")
	protected String active;
	  
	  @ExceptionHandler(value=Exception.class)  
	  public ModelAndView MethodArgumentNotValidHandler(HttpServletRequest request,HttpServletResponse response,  Exception exception) throws Exception   {  
		  ModelAndView mv = new ModelAndView("/common/error");
		  exception.printStackTrace();
		  String msg = "";
		  if(exception instanceof AuthorizationException) {
			  msg="没有可用的访问权限！";
		  }else {
			  msg = "系统内部错误，请联系管理员！";
		  }
		  if (!(request.getHeader("accept").contains("application/json")  || (request.getHeader("X-Requested-With")!= null && request
	              .getHeader("X-Requested-With").contains("XMLHttpRequest") ))) {
			  mv.addObject("error", msg);
			  return mv;  
	          } else {// JSON格式返回
	            try {
	                 PrintWriter writer = response.getWriter();
	         		ObjectMapper objectMapper = new ObjectMapper();
	                 writer.write(objectMapper.writeValueAsString(AjaxResult.error(msg)));
	                 writer.flush();
	              } catch (IOException e) {
	                 e.printStackTrace();
	              }
	            return null;
	         }
	  	}  
	 
  
}  
