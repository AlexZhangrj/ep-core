package com.zhrenjie04.alex.user.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.user.domain.Privilege;

/**
 * @author 张人杰
 */
@Repository("role2PrivilegeDao")
public interface Role2PrivilegeDao {
	List<Privilege> queryAllPrivilegesByRoleId(String roleId);
}
