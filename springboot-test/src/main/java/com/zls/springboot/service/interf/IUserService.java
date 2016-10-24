package com.zls.springboot.service.interf;

import org.springframework.web.multipart.MultipartFile;

import com.zls.springboot.common.OperationResult;
import com.zls.springboot.dao.model.User;
import com.zls.springboot.request.form.LoginFormBean;
import com.zls.springboot.request.form.QueryUserFormBean;
import com.zls.springboot.request.form.RegistFormBean;

public interface IUserService {

  OperationResult regist(RegistFormBean registForm);

  OperationResult queryUserById(Long id);

  OperationResult updateUser(User user, MultipartFile avatar);

  OperationResult login(LoginFormBean form);
  
  /**
   * 查询用户列表
   * @param form
   * @return
   */
  OperationResult queryUserList(QueryUserFormBean form);
}
