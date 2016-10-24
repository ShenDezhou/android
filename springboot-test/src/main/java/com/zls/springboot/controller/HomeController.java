package com.zls.springboot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  private static final Logger logger = LogManager.getLogger(HomeController.class);

  /**
   * 用户首页.
   */
  @RequestMapping(value = "/")
  public String home() {
    logger.debug("用户首页");
    return "Hello Spring-boot";
  }
}
