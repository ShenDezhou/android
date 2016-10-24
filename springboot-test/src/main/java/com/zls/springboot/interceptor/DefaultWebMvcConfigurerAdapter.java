package com.zls.springboot.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * SpringMVC 配置加载组件,用来注册拦截器.
 * @author 承项
 * @date 2015年9月29日下午8:03:27
 */
@Configuration
public class DefaultWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter{
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		ExampleInterceptor exampleInterceptor = applicationContext.getBean(ExampleInterceptor.class);
		registry.addInterceptor(exampleInterceptor).addPathPatterns(new String[]{"/user/**"});
	}
}
