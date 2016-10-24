package com.zls.springboot.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.zls.springboot.dao.model.User;

/**
 * JPA user 数据访问对象.
 * @author 承项
 * @date 2015年9月30日下午5:10:05
 */
public interface UserRepository extends JpaRepository<User, Long>{
	
  User findByUsername(String username);
}
