package com.zhrenjie04.alex.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.user.domain.Privilege;

/**
 * @author 张人杰
 */
@Repository("groupDao")
public interface Role2PrivilegeDao {
	List<Privilege> queryAllPrivilegesByRoleId(String roleId);
}
