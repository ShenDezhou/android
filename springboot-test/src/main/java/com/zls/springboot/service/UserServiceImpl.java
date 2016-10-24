package com.zls.springboot.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zls.springboot.common.OperationResult;
import com.zls.springboot.common.Pagination;
import com.zls.springboot.config.FileUploadConfig;
import com.zls.springboot.dao.model.User;
import com.zls.springboot.dao.repository.UserRepository;
import com.zls.springboot.request.form.LoginFormBean;
import com.zls.springboot.request.form.QueryUserFormBean;
import com.zls.springboot.request.form.RegistFormBean;
import com.zls.springboot.service.interf.IUserService;
import com.zls.springboot.util.FileOperationUtil;

@Service
public class UserServiceImpl implements IUserService {

  private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private FileUploadConfig fileUploadConfig;

  @Override
  public OperationResult regist(RegistFormBean registForm) {
    logger.debug("UserService addUser, registForm={}.", registForm);
    User user = null;

    user = new User();
    Date nowTime = new Date();
    user.setUsername(registForm.getUsername());
    user.setPassword(registForm.getPassword());
    user.setCreateTime(nowTime);
    user = userRepository.save(user);
    logger.debug("UserService addUser,调用JPA之后. user={}.", user);
    return OperationResult.success("注册成功!");
  }

  @Override
  public OperationResult queryUserById(Long id) {
    logger.debug("UserService queryUserById, id={}.", id);
    User user = userRepository.findOne(id);
    if (user == null) {
      return OperationResult.fail("未查找到用户");
    }
    Map<String, Object> result = new HashMap<>();
    result.put("username", user.getUsername());
    result.put("id", user.getId());
    return OperationResult.success(result);
  }

  @Override
  public OperationResult updateUser(User user, MultipartFile avatar) {
    logger.debug("UserService updateUser, 更改后user={}.", user);

    /**
     * 存储文件
     */
    try {
      String fileName = null;
      String originName = null;
      String fileType = null;
      File targetFile = null;

      fileName = UUID.randomUUID().toString();
      // 根据客户端的文件系统获取文件类型(后缀)
      originName = avatar.getOriginalFilename();
      fileType = FileOperationUtil.getTypeByOriginName(originName);

      fileName = fileUploadConfig.getSavePath() + File.separator + fileName + "." + fileType;

      logger.debug("存储文件,存储路径filePath={}.", fileName);
      targetFile = new File(fileName);

      FileOperationUtil.wirteToFile(avatar.getInputStream(), targetFile);

      /**
       * 是否删除暂存文件
       */
      if (fileUploadConfig.isTemp()) {
        FileOperationUtil.deleteFile(targetFile);
      }
    } catch (Exception e) {
      logger.error("更新用户信息,保存头像错误,user={}.", user);
      logger.error("", e);
    }
    return null;
  }

  @Override
  public OperationResult login(LoginFormBean form) {

    User user = null;
    String username = form.getUsername();
    user = userRepository.findByUsername(username);

    if (user == null) {
      return OperationResult.fail("用户尚未注册");
    }

    String password = form.getPassword();
    if (!password.equals(user.getPassword())) {
      return OperationResult.fail("用户名/密码不匹配");
    }

    Map<String, Object> result = new HashMap<>();
    result.put("id", user.getId());
    result.put("username", user.getUsername());
    return OperationResult.success(result);
  }

  @Override
  public OperationResult queryUserList(QueryUserFormBean form) {
    
    List<User> userLists = null;
    List<Map<String, Object>> respMap = null;
    Pagination pagination = null;
    Page<User> page = null;
    
    Pageable pageable = new PageRequest(form.getCurrPage()-1, form.getPageSize());
    page = userRepository.findAll(pageable);
    
    userLists = page.getContent();
    if (userLists == null || userLists.isEmpty()){
      respMap = Collections.emptyList();
      pagination = new Pagination(0L, form.getCurrPage(),null, form.getPageSize());
      return OperationResult.success(respMap, pagination);
    }
    
    /**
     * 构建成功响应
     */
    respMap = new ArrayList<>(userLists.size());
    pagination = new Pagination(page.getTotalElements(), form.getCurrPage(), null, form.getPageSize(), page.getNumberOfElements());
    for (User user : userLists){
      Map<String, Object> respItem = new HashMap<>();
      respItem.put("id", user.getId());
      respItem.put("username", user.getUsername());
      respMap.add(respItem);
    }
    return OperationResult.success(respMap, pagination);
  }
}
