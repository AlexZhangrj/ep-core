package com.zhrenjie04.alex.user.dao;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.zhrenjie04.alex.core.GenericDao;
import com.zhrenjie04.alex.manager.domain.JobUserRole;
import com.zhrenjie04.alex.manager.domain.Role;
/**
 * @author 张人杰
 */
@Repository("jobUserRoleDao")
public interface JobUserRoleDao extends GenericDao<JobUserRole>{
	List<Role> queryAllRolesByJobIds(HashMap<String,Object>params);
}
