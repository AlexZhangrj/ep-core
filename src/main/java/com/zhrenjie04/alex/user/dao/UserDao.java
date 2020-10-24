package com.zhrenjie04.alex.user.dao;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.core.User;

/**
 * @author 张人杰
 */
@Repository("userDao")
public interface UserDao {
	User queryObjectById(String id);
}
