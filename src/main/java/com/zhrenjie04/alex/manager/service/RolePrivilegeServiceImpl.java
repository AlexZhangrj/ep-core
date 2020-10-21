package com.zhrenjie04.alex.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhrenjie04.alex.core.AbstractGenericService;
import com.zhrenjie04.alex.manager.domain.RolePrivilege;
import com.zhrenjie04.alex.manager.dao.RolePrivilegeDao;
/**
 * @author 张人杰
 */
@Service("rolePrivilegeService")
public class RolePrivilegeServiceImpl extends AbstractGenericService<RolePrivilege,RolePrivilegeDao> implements RolePrivilegeService {

	@Autowired
	RolePrivilegeDao rolePrivilegeDao;

	@Override
	protected RolePrivilegeDao getDao() {
		return rolePrivilegeDao;
	}	
}
