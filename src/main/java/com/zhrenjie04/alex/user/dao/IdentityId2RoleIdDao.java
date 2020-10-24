package com.zhrenjie04.alex.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.user.domain.Group;
import com.zhrenjie04.alex.user.domain.IdentityId2RoleId;

/**
 * @author 张人杰
 */
@Repository("identityId2RoleIdDao")
public interface IdentityId2RoleIdDao {
	List<IdentityId2RoleId> queryAllByIdentityId(String identityId);
}
