package com.sgnbs.ms.interceptor;


import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sgnbs.common.constants.Constants;
import com.sgnbs.common.utils.SpringUtil;
import com.sgnbs.common.utils.SysUserUtil;
import com.sgnbs.ms.annotation.SysLogAnno;
import com.sgnbs.ms.dao.SysLogDAO;
import com.sgnbs.ms.model.SysLog;


public class Interceptor implements HandlerInterceptor {

	private SysLogDAO sysLogDAO;
	{
		sysLogDAO = SpringUtil.getBean(SysLogDAO.class);
	}
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  throws Exception {
		HttpSession session=request.getSession();
		if(null!=session && null!=session.getAttribute(Constants.SYS_USER_TOKEN)){
			request.setAttribute("requestStartTime", new Date().getTime());
			return true;
		}else{
			String ServletPath=request.getContextPath();
			response.sendRedirect(ServletPath+"/toLogin");
			return false;
		}
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  ModelAndView modelAndView) throws Exception {
    	if(null!=modelAndView && response.getStatus()==404) {
    		modelAndView.setViewName("/common/404");
    	}
    	if(null!=modelAndView &&  response.getStatus()==500) {
    		request.setAttribute("error", "系统内部错误，请联系管理员！");
    		modelAndView.setViewName("/common/error");
    	}
         
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)   throws Exception {
    	//记录日志 
    	//如果不是映射到方法直接通过
    	if (!(handler instanceof HandlerMethod)) {
              return;
          }
          HandlerMethod handlerMethod = (HandlerMethod) handler;
          Method method = handlerMethod.getMethod();
          SysLogAnno lognno =  method.getAnnotation(SysLogAnno.class);
          if (lognno != null) {
                SysLog syslog = new SysLog();
	  			syslog.setUserId(SysUserUtil.getUser().getId());
	  			syslog.setActiondesc(SysUserUtil.getUser().getName()+" "+lognno.value());
	  			syslog.setActiontime(new Date());
	  			sysLogDAO.insert(syslog);
          }
    }
    
    
}