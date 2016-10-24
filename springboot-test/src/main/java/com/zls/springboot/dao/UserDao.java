package com.zls.springboot.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.zls.springboot.dao.interf.IUserDao;
import com.zls.springboot.dao.model.User;

@Repository
public class UserDao implements IUserDao {

	private static final Logger logger = LogManager.getLogger(UserDao.class);
	
	@Override
	public int insertUser(User user) {
		logger.debug("UserDao insertUser, user={}.", user);
		return 1;
	}

	@Override
	public User selectUserById(Long id) {
		logger.debug("UserDao selectUserById, id={}.", id);
		User user = new User();
		user.setId(id);
		user.setUsername("username00" + id);
		user.setPassword("password00" + id);
		return user;
	}

}
