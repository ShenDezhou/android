package com.zls.springboot.dao.interf;

import com.zls.springboot.dao.model.User;

public interface IUserDao {

	int insertUser(User user);
	
	User selectUserById(Long id);
}
