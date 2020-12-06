package com.zhrenjie04.alex.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.core.Identity;

/**
 * @author 张人杰
 */
@Repository("identityDao")
public interface IdentityDao {
	List<Identity>queryListByUserId(String userId);
}
