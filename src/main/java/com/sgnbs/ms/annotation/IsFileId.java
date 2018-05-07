package com.sgnbs.ms.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for file id
 * @author: tianj 
 * @since 3.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Deprecated
public @interface IsFileId {
	/**
	 * true 为多文件。false为单文件
	 * @return
	 */
	 boolean value() default true;
	 
	 String name();
}
