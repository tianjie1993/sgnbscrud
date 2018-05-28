package com.sgnbs;

import java.nio.charset.Charset;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.sgnbs.common.servlet.ValidateCodeServlet;
import com.sgnbs.ms.interceptor.Interceptor;


@SpringBootApplication
@EnableScheduling
public class MsApplication  extends WebMvcConfigurerAdapter{

	 @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor()).addPathPatterns("/**").excludePathPatterns("/toLogin","/login","/vcode","/error");
        super.addInterceptors(registry);
    }
	 
	  @Bean
     public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
     }

    @Bean
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
	  
	  /**  
	     * 文件上传配置  
	     * @return  
	     */  
    @Bean  
    public MultipartConfigElement multipartConfigElement() {  
        MultipartConfigFactory factory = new MultipartConfigFactory();  
        //文件最大  
        factory.setMaxFileSize("102400KB"); //KB,MB  
        /// 设置总上传数据总大小  
        factory.setMaxRequestSize("1024000KB");  
        return factory.createMultipartConfig();  
    }  
    
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
    	ValidateCodeServlet validateCondeServlet = new ValidateCodeServlet();
    	ServletRegistrationBean servlet = new ServletRegistrationBean(validateCondeServlet, "/vcode");// ServletName默认值为首字母小写
    	return servlet;
    }
 

	public static void main(String[] args) {
		SpringApplication.run(MsApplication.class, args);
	}
}
