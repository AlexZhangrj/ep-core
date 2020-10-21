package com.zhrenjie04.alex.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhrenjie04.alex.core.AbstractGenericService;
import com.zhrenjie04.alex.manager.domain.JobUserOrgPosition;
import com.zhrenjie04.alex.manager.dao.JobUserOrgPositionDao;
/**
 * @author 张人杰
 */
@Service("userOrgPositionService")
public class JobUserOrgPositionServiceImpl extends AbstractGenericService<JobUserOrgPosition,JobUserOrgPositionDao> implements JobUserOrgPositionService {

	@Autowired
	JobUserOrgPositionDao userOrgPositionDao;

	@Override
	protected JobUserOrgPositionDao getDao() {
		return userOrgPositionDao;
	}	
}
