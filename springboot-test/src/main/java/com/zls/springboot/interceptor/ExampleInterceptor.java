package com.zls.springboot.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 测试拦截器.
 * @author 承项
 * @date 2015年9月29日下午7:50:59
 */
@Component
public class ExampleInterceptor extends HandlerInterceptorAdapter{

	private static final Logger logger = LogManager.getLogger(ExampleInterceptor.class); 
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		logger.info("ExampleInterceptor 拦截器,处理之前.");
		return true;
	}
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		logger.info("ExampleInterceptor 拦截器,离开controller之后,试图渲染之前.");
	}
}
