package com.zls.springboot.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.zls.springboot.common.OperationResult;
import com.zls.springboot.dao.model.User;
import com.zls.springboot.request.form.LoginFormBean;
import com.zls.springboot.request.form.QueryUserFormBean;
import com.zls.springboot.request.form.RegistFormBean;
import com.zls.springboot.service.interf.IUserService;

@RequestMapping("/user")
@RestController
public class UserController {

  private static final Logger logger = LogManager.getLogger(UserController.class);

  @Autowired
  private IUserService userServiceImpl;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public OperationResult queryUser(@PathVariable Long id) {
    logger.debug("Controller queryUser, id={}.", id);
    return userServiceImpl.queryUserById(id);
  }

  /**
   * 更改用户信息.
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.POST)
  public OperationResult updateUser(@PathVariable Long id, User user,
      @RequestParam("avatar") MultipartFile file) {
    logger.debug("Controller updateUser, id={}.", id);
    return userServiceImpl.updateUser(user, file);
  }

  /**
   * 用户注册.
   */
  @RequestMapping(value = "/regist", method = RequestMethod.POST)
  public OperationResult login(@RequestBody RegistFormBean form) {
    logger.debug("Controller regist, form={}.", form);
    return userServiceImpl.regist(form);
  }

  /**
   * 用户登陆.
   */
  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public OperationResult regist(@RequestBody LoginFormBean form) {
    logger.debug("Controller login, form={}.", form);
    return userServiceImpl.login(form);
  }

  /**
   * 用户列表.
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public OperationResult userLists(QueryUserFormBean form) {
    logger.debug("Controller userLists, form={}.", form);
    return userServiceImpl.queryUserList(form);
  }
}
