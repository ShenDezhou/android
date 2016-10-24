package com.zls.springboot;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeTest;

@SpringApplicationConfiguration(classes=App.class)
@WebAppConfiguration
public class TestBase extends AbstractTestNGSpringContextTests{

	@BeforeTest
	protected void before(){
		System.out.println("---- 测试开始 ----");
	}
}
