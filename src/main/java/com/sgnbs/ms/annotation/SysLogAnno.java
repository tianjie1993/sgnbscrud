package com.sgnbs.ms.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for syslog
 * @author: tianj 
 * @version: 2017年9月20日
 * @since 2.0
 * @see Interceptor
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SysLogAnno {
	
	String value();

}
